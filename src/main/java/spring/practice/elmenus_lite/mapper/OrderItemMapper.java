package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.OrderItemModel;

    @Mapper(componentModel = "spring")
    public interface OrderItemMapper {

        @Mapping(target = "menuItemId" , source = "orderItem.menuItem.id")
        @Mapping(target = "menuItemName", expression = "java(getMenuItemName(orderItem.getMenuItem()))")
        @Mapping( target = "totalPrice",
                expression = "java(orderItem.getUnitPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity())).intValue())")
        @Mapping(target = "restaurantName", source = "orderItem.menuItem.menuModel.restaurantModel.restaurantName")
        @Mapping(target = "quantity", source = "orderItem.quantity")
        OrderItemDto toOrderItemResponseDto(OrderItemModel orderItem);

        default String getMenuItemName(MenuItemModel menuItem) {
            return menuItem != null ? menuItem.getMenuItemName() : null;
        }
}
