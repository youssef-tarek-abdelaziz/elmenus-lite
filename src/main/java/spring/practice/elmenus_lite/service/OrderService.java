package spring.practice.elmenus_lite.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.OrderStatusEnum;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderStatusRepository orderStatusRepository;
    private OrderRepository orderRepository;

    public void updateOrderStatus(Integer orderId, Integer statusId) {
        OrderModel order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {throw new RuntimeException("Order not found");}

        OrderStatusModel newStatus = orderStatusRepository.findById(statusId).orElseThrow();

        OrderStatusEnum currentStatus = order.getOrderStatus().getOrderStatus();

        if(!currentStatus.isAllowedTransition(newStatus.getOrderStatus())){
            throw new IllegalArgumentException("Order status is not allowed transition");
        }


        order.setOrderStatus(newStatus);
        orderRepository.save(order);
    }

}
