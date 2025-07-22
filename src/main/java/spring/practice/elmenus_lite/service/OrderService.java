package spring.practice.elmenus_lite.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.constants.OrderStatus;
import spring.practice.elmenus_lite.dto.*;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.mapper.OrderModelDtoMapper;
import spring.practice.elmenus_lite.model.AddressModel;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;
import spring.practice.elmenus_lite.util.OrderUtility;
import spring.practice.elmenus_lite.util.OrderValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderModelDtoMapper orderModelDtoMapper;
    private final OrderValidator orderValidator;
    private final OrderUtility orderUtility;
    private final AddressService addressService;
    private final PromotionService promotionService;
    private final OrderStatusRepository orderStatusRepository;

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDetails(Integer orderId) {
//        OrderModel orderModel = orderValidator.validateOrderExistance(orderId);
        OrderModel orderModel = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.ORDER_NOT_FOUND.getFinalMessage(List.of(orderId))));;
        List<OrderItemResponseDto> items = orderModelDtoMapper.toOrderItemResponseDtoList(new ArrayList<>(orderModel.getOrderItems()));
        return orderModelDtoMapper.mapToOrderResponseDto( orderModel,items , orderModelDtoMapper.mapAddressToDto(orderModel.getAddress()));
    }



    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllOrders(Integer customerId , Pageable page) {

        CustomerModel customerModel = orderValidator.validateOrdersExistanceByCustomerId(customerId);

//        Page<OrderModel> customerOrders = orderRepository.findByCustomerId(customerId , page);
        Page<OrderModel> customerOrders = orderRepository.findByCustomerId(customerId , page);
        return customerOrders.map( order -> {
            List<OrderItemResponseDto> items = orderModelDtoMapper.toOrderItemResponseDtoList(
                    new ArrayList<>(order.getOrderItems())
            );

            return orderModelDtoMapper.mapToOrderResponseDto(
                    order,
                    items,
                    orderModelDtoMapper.mapAddressToDto(order.getAddress())
            );
        });

    }


    @Transactional
    public void updateOrderStatus(Integer orderId, String newStatusName) {
        OrderModel order = orderValidator.validateByOrderId(orderId);
        String currentStatus = order.getOrderStatus().getOrderStatusName();

        String statusName = newStatusName.toUpperCase();
        OrderStatusModel newStatus = orderStatusRepository.findByOrderStatusName(statusName)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_ORDER_STATUS.getFinalMessage(List.of(statusName))));

        if ( !orderUtility.canTransitionTo(currentStatus, statusName) ){
            throw new BadRequestException(ErrorMessage.INVALID_ORDER_STATUS_TRANSITION.getFinalMessage(List.of(statusName)));
        }

        if (!statusName.equals(OrderStatus.CANCELLED)) {
            String previousStatus = newStatus.getPreviousStatus().getOrderStatusName();

            if (!previousStatus.equals(currentStatus)) {
                throw new BadRequestException(ErrorMessage.INVALID_ORDER_STATUS_TRANSITION.getFinalMessage(List.of(statusName)));
            }
        }
        order.setOrderStatus(newStatus);
        orderRepository.save(order);
    }

    @Transactional
    public void makeOrder(OrderDto orderDto) {
        orderDto.setOrderDate(LocalDateTime.now());
        OrderValidationSuccessResultDro orderValidationDto = orderValidator.validateOrderInfo(orderDto);
        AddressModel addressModel = null;
        if(orderValidationDto.getAddressModel() == null) {
            addressModel = addressService.addAddress(orderDto.getCustomerId(), orderDto.getAddress());
        }
        OrderModel orderModel = orderModelDtoMapper.mapOrderDtoToModel(orderDto, orderValidationDto);
        orderModel = orderUtility.setupOrderForSaving(orderModel, addressModel);
        BigDecimal totalPrice = orderModel.getOrderItems()
                .stream()
                .map(orderItem -> orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())) )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountAmount = promotionService.getPromotionDiscount(orderModel.getPromotion(), totalPrice);
        orderModel.setTotal(totalPrice);
        orderModel.setSubtotal(totalPrice.subtract(discountAmount));
        orderModel.setDiscountAmount(discountAmount);
        orderRepository.save(orderModel);
    }

}
