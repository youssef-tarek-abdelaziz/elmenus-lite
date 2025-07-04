package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.elmenus_lite.apiDto.MenuAPiRequestDto;
import spring.practice.elmenus_lite.apiDto.MenuApiDto;
import spring.practice.elmenus_lite.apiDto.MenuItemApiDto;
import spring.practice.elmenus_lite.dto.MenuDto;
import spring.practice.elmenus_lite.dto.MenuItemDto;
import spring.practice.elmenus_lite.dto.OrderItemResponseDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.mapper.MenuApiDtoMapper;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.repository.MenuItemRepository;
import spring.practice.elmenus_lite.repository.MenuRepository;
import spring.practice.elmenus_lite.repository.OrderItemRepository;
import spring.practice.elmenus_lite.repository.RestaurantRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuApiDtoMapper menuApiDtoMapper;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemRepository orderItemRepository;

  //   without pagination
//    @Transactional(readOnly = true)
//    public MenuDto getMenuDetails(Integer menuId){
//        MenuModel menuModel = menuRepository.findById(menuId)
//                .orElseThrow(() -> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));
//        List<MenuItemModel> menuItemModels = menuItemRepository.findAllByMenuModelId(menuId);
//        List<MenuItemDto> menuItems = menuApiDtoMapper.toMenuItemDtoList(menuItemModels);
//        return menuApiDtoMapper.toMenuDto(menuModel , menuItems);
//    }


    // pagination without page info

//public MenuDto getMenuDetails(Integer menuId, Pageable pageable) {
//    MenuModel menuModel = menuRepository.findById(menuId)
//            .orElseThrow(() -> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));
//
//    Page<MenuItemModel> menuItemPage = menuItemRepository.findByMenuModelId(menuId, pageable);
//
//    List<MenuItemDto> menuItems = menuApiDtoMapper.toMenuItemDtoList(menuItemPage.getContent());
//
//    MenuDto menuDto = menuApiDtoMapper.toMenuDto(menuModel, menuItems);
//
//    return menuDto;
//}

    public Page<MenuDto> getMenuDetails(Integer menuId, Pageable pageable) {
        MenuModel menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));

        Page<MenuItemModel> page = menuItemRepository.findByMenuModelId(menuId, pageable);
        Page<MenuItemDto> items = page.map(menuApiDtoMapper::toMenuItemDto);
        return menuApiDtoMapper.toMenuDtoPage(menu , items);
    }

    @Transactional(readOnly = true)
    public Page<MenuDto> getAllMenusForRestaurant(Integer restaurantId, Pageable page){
        RestaurantModel restaurantModel = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new BadRequestException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage(List.of(restaurantId))));
        Page<MenuModel> restaurantMenusModels = menuRepository.findByRestaurantModel_Id(restaurantId, page);
        return restaurantMenusModels.map(menu -> {
            List<MenuItemModel> menuItems = menuItemRepository.findByMenuModelId(menu.getId());
            List<MenuItemDto> menuItemDtos = menuApiDtoMapper.toMenuItemDtoList(menuItems);
            return menuApiDtoMapper.toMenuDto(menu, menuItemDtos);
        });
    }

//    @Transactional(readOnly = true)
//    public Page<MenuDto> getAllMenusForRestaurant(Integer restaurantId, Pageable page){
//        RestaurantModel restaurantModel = restaurantRepository.findById(restaurantId)
//                .orElseThrow(()-> new BadRequestException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage(List.of(restaurantId))));
//        Page<MenuModel> restaurantMenusModels = menuRepository.findByRestaurantModel_Id(restaurantId, page);
//        return restaurantMenusModels.map(menu -> {
//            Page<MenuItemModel> itemPage = menuItemRepository.findByMenuModelId(menu.getId(), page);
//            List<MenuItemDto> menuItemDtos = menuApiDtoMapper.toMenuItemDtoList(itemPage.getContent());
//            return menuApiDtoMapper.toMenuDto(menu, menuItemDtos);
//        });
//    }

    @Transactional
    public void addNewMenu(MenuAPiRequestDto menuAPiRequestDto){
        MenuModel menuModel = menuApiDtoMapper.toMenuModelFromRequest(menuAPiRequestDto , restaurantRepository);
        menuRepository.save(menuModel);
    }

    @Transactional
    public void updateMenuName(Integer menuId, String menuName){
       MenuModel menuModel = menuRepository.findById(menuId)
                .orElseThrow(()-> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));
       menuModel.setMenuName(menuName);

    }


    @Transactional
    public void deleteMenu(Integer menuId) {
        MenuModel menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MENU_NOT_FOUND.getFinalMessage(List.of(menuId))));

        List<MenuItemModel> menuItems = menuItemRepository.findAllByMenuModelId(menuId);
        menuItems.forEach(item -> {
            List<OrderItemModel> orderItems = orderItemRepository.findAllByMenuItemId(item.getId());
            orderItemRepository.deleteAll(orderItems);
        });
        menuItemRepository.deleteAll(menuItems);
        menuRepository.delete(menu);
    }

    public int countMenuSize(Integer menuId) {
        return menuItemRepository.countByMenuModel_Id(menuId);

    }

    public int countRestaurantSize(Integer restaurantId){
        return menuRepository.countByRestaurantModel_Id(restaurantId);
    }
}
