package spring.practice.elmenus_lite.mapper;

import org.locationtech.jts.geom.Point;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import spring.practice.elmenus_lite.apiDto.AddressApiDto;
import spring.practice.elmenus_lite.apiDto.OrderItemApiDto;
import spring.practice.elmenus_lite.dto.AddressDto;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.dto.OrderValidationSuccessResultDro;
import spring.practice.elmenus_lite.model.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {OrderItemMapper.class, AddressModelDtoMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderModelDtoMapper {


    @Mapping(target = "orderItems", expression = "java(mapOrderItemDtosToModels(orderDto.getItems(), orderValidationDto.getMenuItems()))")
    @Mapping(target = "orderTracking", expression = "java(new spring.practice.elmenus_lite.model.OrderTrackingModel (java.time.Duration.ofHours(2)))")
    @Mapping(target = "address", source = "orderValidationDto.addressModel")
    @Mapping(target = "orderStatus",  expression = "java(mapOrderStatus(orderDto.getOrderStatus()))")
    OrderModel mapOrderDtoToModel(OrderDto orderDto, OrderValidationSuccessResultDro orderValidationDto);

    default List<OrderItemModel> mapOrderItemDtosToModels(List<OrderItemDto> orderItemDtos, List<MenuItemModel> menuItemModels) {
        List<OrderItemModel> orderItemModels = new ArrayList<>();
        Map<Integer, MenuItemModel> menuItemsMap = menuItemModels.stream().collect(Collectors.toMap(MenuItemModel::getId, Function.identity()));
        for(OrderItemDto orderItemDto : orderItemDtos) {
            OrderItemModel orderItemModel = new OrderItemModel();
            orderItemModel.setQuantity(orderItemDto.getQuantity());
            orderItemModel.setMenuItem(menuItemsMap.get(orderItemDto.getMenuItemId()));
            orderItemModel.setUnitPrice(BigDecimal.valueOf(menuItemsMap.get(orderItemDto.getMenuItemId()).getPrice()));
            orderItemModels.add(orderItemModel);
        }
        return orderItemModels;
    }

//    List<OrderItemDto> toOrderItemResponseDtoList(List<OrderItemModel> orderItemModels);

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "customerId", source = "order.customer.id")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPriceFromDtos(orderItems))")
    @Mapping(target = "restaurantName", source = "orderItems", qualifiedByName = "extractRestaurantNameFromDtos")
    @Mapping(target = "orderStatus",  expression = "java(order.getOrderStatus().getOrderStatusName())")
    OrderDto mapToOrderResponseDto(OrderModel order, List<OrderItemDto> orderItems, AddressDto address);

    @Named("pointToString")
    default String pointToString(Point point) {
        return point != null ? point.getX() + "," + point.getY() : null;
    }

    @Mapping(target = "location", ignore = true)
    AddressDto mapAddressToDto(AddressModel address);

    @Named("extractRestaurantNameFromDtos")
    default String extractRestaurantNameFromDtos(List<OrderItemDto> items) {
        return Optional.ofNullable(items)
                .flatMap(list -> list.stream().findFirst())
                .map(OrderItemDto::getRestaurantName)
                .orElse(null);
    }

    default Integer calculateTotalPriceFromDtos(List<OrderItemDto> items) {
        return items.stream()
                .mapToInt(OrderItemDto::getTotalPrice)
                .sum();
    }

    default OrderStatusModel mapOrderStatus(String statusName) {
        if (statusName == null) return null;
        OrderStatusModel status = new OrderStatusModel();
        status.setOrderStatusName(statusName);
        return status;
    }

     List<OrderItemDto> toOrderItemResponseDtoList(List<OrderItemModel> items);

}
