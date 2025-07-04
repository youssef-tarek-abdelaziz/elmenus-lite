package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import spring.practice.elmenus_lite.apiDto.MenuAPiRequestDto;
import spring.practice.elmenus_lite.apiDto.MenuApiDto;
import spring.practice.elmenus_lite.apiDto.MenuItemApiDto;
import spring.practice.elmenus_lite.apiDto.MenuItemApiRequestDto;
import spring.practice.elmenus_lite.dto.MenuDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.MenuModel;
import spring.practice.elmenus_lite.model.RestaurantModel;
import spring.practice.elmenus_lite.repository.MenuRepository;
import spring.practice.elmenus_lite.repository.RestaurantRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MenuApiDtoMapper {

    @Mapping(target = "id" , source = "id")
    // restaurantId
    MenuModel toMenuModel(MenuDto menuDto);

    @Mapping(target = "id" , source = "menuModel.id")
    @Mapping(target = "restaurantName" , source = "menuModel.restaurantModel.restaurantName")
    @Mapping(target = "menuItems", source = "menuItemDto")
    MenuDto toMenuDto(MenuModel menuModel , List<MenuItemDto> menuItemDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "itemName", source = "menuItemName")
    MenuItemDto toMenuItemDto(MenuItemModel menuItem);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "menuItemName", source = "itemName")
    //menuModel -> menuId
    MenuItemModel toMenuItemModel(MenuItemDto menuItemDto);

    @Mapping(target = "id", source = "id")
    MenuApiDto toMenuApiDto(MenuDto menuDto);

    @Mapping(target = "id", source = "id")
    MenuDto toMenuDtoFromMenuApiDto(MenuApiDto menuApiDto);

    @Mapping(target = "id", source = "id")
    MenuItemApiDto toMenuItemApiDto(MenuItemDto menuDto);

    List<MenuItemDto> toMenuItemDtoList(List<MenuItemModel> menuItemModels);

    List<MenuItemApiDto> toMenuItemApiDtoList(List<MenuItemDto> menuItemDtos);

    @Mapping(target = "menuName", source = "menuName")
    @Mapping(target = "restaurantName" , expression = "java(mapRestaurantName(menuAPiRequestDto.getRestaurantId(), repository))")
    MenuApiDto toMenuApiFromMenuApiRequestDto(MenuAPiRequestDto menuAPiRequestDto, @Context RestaurantRepository repository);

    default String mapRestaurantName(Integer restaurantId, @Context RestaurantRepository restaurantRepository){
        RestaurantModel restaurantModel = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage(List.of(restaurantId))));
        return restaurantModel.getRestaurantName();

    }

    @Mapping(target = "restaurantModel",expression = "java(mapRestaurant(dto.getRestaurantId(), repository))")
    MenuModel toMenuModelFromRequest(MenuAPiRequestDto dto, @Context RestaurantRepository repository);

    default RestaurantModel mapRestaurant(Integer restaurantId, @Context RestaurantRepository repository) {
        return repository.findById(restaurantId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage(List.of(restaurantId))));
    }

    @Mapping(target = "menuItemName", source = "menuItemApiRequestDto.itemName")
    @Mapping(target = "price", source = "menuItemApiRequestDto.price")
    @Mapping(target = "menuModel",expression = "java(mapMenu(menuItemApiRequestDto.getMenuId(), repository))")
    MenuItemModel toMenuItemModelFromRequest(MenuItemApiRequestDto menuItemApiRequestDto , Boolean available,@Context MenuRepository repository );

    default MenuModel mapMenu(Integer menuId, @Context MenuRepository repository) {
        return repository.findById(menuId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));
    }


    default Page<MenuApiDto> mapMenuApiDtoToPage(Page<MenuDto> menuDto){
        return menuDto.map(this::toMenuApiDto);
    }

   default  Page<MenuDto> toMenuDtoPage(MenuModel menu, Page<MenuItemDto> items){
       List<MenuItemDto> itemList = items.getContent();
       MenuDto menuDto = toMenuDto(menu, itemList);
       return new PageImpl<>(
               List.of(menuDto),
               items.getPageable(),
               items.getTotalElements()
       );
   }

}


