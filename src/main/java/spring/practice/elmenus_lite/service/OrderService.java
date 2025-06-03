package spring.practice.elmenus_lite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.dto.OrderValidationSuccessResultDro;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.mapper.OrderModelDtoMapper;
import spring.practice.elmenus_lite.model.AddressModel;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;
import spring.practice.elmenus_lite.util.CustomerValidator;
import spring.practice.elmenus_lite.util.OrderUtility;
import spring.practice.elmenus_lite.util.OrderValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderModelDtoMapper orderModelDtoMapper;
    private final OrderValidator orderValidator;
    private final OrderUtility orderUtility;
    private final AddressService addressService;
    private final PromotionService promotionService;



    @Transactional(readOnly = true)
    public OrderDto getOrderDetails(Integer orderId) {
        OrderModel orderModel = orderValidator.validateOrderExistance(orderId);
        List<OrderItemDto> items = orderModelDtoMapper.toOrderItemResponseDtoList(new ArrayList<>(orderModel.getOrderItems()));
        return orderModelDtoMapper.mapToOrderResponseDto( orderModel,items , orderModelDtoMapper.mapAddressToDto(orderModel.getAddress()));
    }



    @Transactional(readOnly = true)
    public Page<OrderDto> getAllOrders(Integer customerId , Pageable page) {

//        CustomerModel customerModel = orderValidator.validateOrdersExistanceByCustomerId(customerId);

        Page<OrderModel> customerOrders = orderRepository.findByCustomerId(customerId , page);

        return customerOrders.map(order -> {
            List<OrderItemDto> items = orderModelDtoMapper.toOrderItemResponseDtoList(
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
        orderModel.setSubtotal(totalPrice.subtract(discountAmount));
        orderModel.setTotal(totalPrice.subtract(discountAmount));
        orderModel.setDiscountAmount(discountAmount);
        orderRepository.save(orderModel);
    }
}
