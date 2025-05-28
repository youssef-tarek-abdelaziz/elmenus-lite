package spring.practice.elmenus_lite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderValidationResultDto;
import spring.practice.elmenus_lite.mapper.OrderModelDtoMapper;
import spring.practice.elmenus_lite.model.OrderItemModel;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.AddressRepository;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.util.OrderUtility;
import spring.practice.elmenus_lite.util.OrderValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderModelDtoMapper orderModelDtoMapper;
    private final OrderValidator orderValidator;
    private final OrderUtility orderUtility;

    @Transactional
    public void makeOrder(OrderDto orderDto) {
        OrderValidationResultDto orderValidationDto = orderValidator.makeOrderValidation(orderDto);
        OrderModel orderModel = orderModelDtoMapper.mapOrderDtoToModel(orderDto, orderValidationDto);

        List<OrderItemModel> items = orderModel.getOrderItems();
        for(OrderItemModel orderItem  : items) {
            orderItem.setOrder(orderModel);
        }
        orderModel.setOrderItems(items);
        OrderStatusModel statusModel  = orderModel.getOrderStatus();
        statusModel.setOrder(orderModel);
        orderModel.setOrderStatus(statusModel);
        orderModel.getAddress().setCustomer(orderValidationDto.getCustomer());
        addressRepository.save(orderModel.getAddress());
        orderUtility.calculateOrderPrice(orderModel, orderModel.getPromotion());
        orderRepository.save(orderModel);
    }
}
