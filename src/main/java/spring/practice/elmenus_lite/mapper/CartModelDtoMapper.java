package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.helperannotations.AuditingFieldsIgnore;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.model.MenuItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CartModelDtoMapper {

    @Mapping(target = "menuItemName", source = "menuItem.menuItemName")
    @Mapping(target = "menuItemId", source = "menuItem.id")
    @Mapping(target = "totalPrice", expression = "java(item.getMenuItem().getPrice() * item.getQuantity())")
    public abstract CartItemResponseDto toCartItemResponseApiDto(CartItemModel item);

    public abstract List<CartItemResponseDto> toCartItemResponseDtoList(List<CartItemModel> items);

    @Mapping(target = "id", source = "cartId")
    @Mapping(target = "cartItemDtoList", source = "items")
    @Mapping(target = "totalPrice", expression = "java(items.stream().mapToInt(CartItemResponseDto::getTotalPrice).sum())")
    public abstract CartResponseDto maptoCartResponseDto(Integer cartId, List<CartItemResponseDto> items);

    @Mapping(target = "id", source = "cartItemDto.id")
    @AuditingFieldsIgnore
    public abstract CartItemModel mapCartItemDtoToModel(CartItemDto cartItemDto, CartModel cart, MenuItemModel menuItem);

    public List<CartItemModel> mapCartItemDtosToModels(List<CartItemDto> cartItemDtos, CartModel cart, List<MenuItemModel> menuItem) {
        List<CartItemModel> cartItemModels = new ArrayList<>();
        Map<Integer, MenuItemModel> menuItemModelMap =  menuItem.stream().collect(Collectors.toMap(MenuItemModel::getId, Function.identity()));
        cartItemDtos.forEach(cartItemDto -> cartItemModels.add(mapCartItemDtoToModel(cartItemDto, cart, menuItemModelMap.get(cartItemDto.getMenuItemId()))));
        return cartItemModels;
    }
}