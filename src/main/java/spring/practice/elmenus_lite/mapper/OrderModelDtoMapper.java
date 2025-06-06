package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.dto.OrderValidationSuccessResultDro;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.OrderItemModel;
import spring.practice.elmenus_lite.model.OrderModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
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

}
