package spring.practice.elmenus_lite.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import spring.practice.elmenus_lite.dto.*;
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

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPriceFromDtos(orderItems))")
    @Mapping(target = "restaurantName", source = "orderItems", qualifiedByName = "extractRestaurantNameFromDtos")
    @Mapping(target = "orderStatus",  expression = "java(order.getOrderStatus().getOrderStatusName())")
    OrderResponseDto mapToOrderResponseDto(OrderModel order, List<OrderItemResponseDto> orderItems, AddressDto address);

    List<OrderItemResponseDto> toOrderItemResponseDtoList(ArrayList<OrderItemModel> orderItemModels);

    @Mapping(target = "location", ignore = true)
    AddressDto mapAddressToDto(AddressModel address);

    @Named("extractRestaurantNameFromDtos")
    default String extractRestaurantNameFromDtos(List<OrderItemResponseDto> items) {
        return Optional.ofNullable(items)
                .flatMap(list -> list.stream().findFirst())
                .map(OrderItemResponseDto::getRestaurantName)
                .orElse(null);
    }


    default OrderStatusModel mapOrderStatus(String statusName) {
        if (statusName == null) return null;
        OrderStatusModel status = new OrderStatusModel();
        status.setOrderStatusName(statusName);
        return status;
    }


    default Integer calculateTotalPriceFromDtos(List<OrderItemResponseDto> items) {
        return items.stream()
                .mapToInt(OrderItemResponseDto::getTotalPrice)
                .sum();
    }



    public abstract List<OrderItemResponseDto> toOrderItemResponseDtoList(List<OrderItemModel> items);


}
