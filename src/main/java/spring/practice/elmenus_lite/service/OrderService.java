package spring.practice.elmenus_lite.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.exception.InvalidOrderStatusTransitionException;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderStatusRepository orderStatusRepository;
    private OrderRepository orderRepository;

    public void updateOrderStatus(Integer orderId, String newStatusName) {

        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND.getFinalMessage(List.of(orderId))));
        String currentStatus = order.getOrderStatus().getOrderStatusName();

        String statusName = newStatusName.toUpperCase();
        OrderStatusModel newStatus = orderStatusRepository.findByOrderStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_ORDER_STATUS.getFinalMessage(List.of(statusName))));

        if ( !canTransitionTo(currentStatus, statusName) ){
            throw new InvalidOrderStatusTransitionException(ErrorMessage.INVALID_ORDER_STATUS_TRANSITION.getFinalMessage(List.of(statusName)));
        }


        if (!statusName.equals("CANCELLED")) {
            String previousStatus = newStatus.getPreviousStatus().getOrderStatusName();

            if (!previousStatus.equals(currentStatus)) {
                throw new InvalidOrderStatusTransitionException(ErrorMessage.INVALID_ORDER_STATUS_TRANSITION.getFinalMessage(List.of(statusName)));
            }
        }

        order.setOrderStatus(newStatus);
        orderRepository.save(order);
    }

    private boolean canTransitionTo(String currentStatus , String newStatus) {
        if ( newStatus.equals("PENDING") || ( newStatus.equals("CANCELLED") && currentStatus.equals("DELIVERED") ) ){
            return false;
        }
        return true;
    }

}
