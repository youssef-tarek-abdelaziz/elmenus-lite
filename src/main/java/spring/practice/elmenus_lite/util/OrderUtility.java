package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.service.OrderStatusService;

@Component
@RequiredArgsConstructor
public class OrderUtility {

    private final OrderStatusService orderStatusService;

    public OrderModel setupOrderForSaving(OrderModel orderModel, AddressModel addressModel) {
        for (OrderItemModel orderItem : orderModel.getOrderItems()) {
            orderItem.setOrder(orderModel);
        }
        OrderStatusModel orderStatusModel = orderStatusService.findInitialOrderStatus();
        orderModel.setOrderStatus(orderStatusModel);
        orderModel.getOrderTracking().setOrderModel(orderModel);
        orderModel.setAddress(addressModel);
        return orderModel;
    }

}
