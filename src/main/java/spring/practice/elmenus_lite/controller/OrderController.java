package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.apiDto.OrderApiDto;
import spring.practice.elmenus_lite.apiDto.OrderResponseApiDto;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderResponseDto;
import spring.practice.elmenus_lite.mapper.OrderApiDtoMapper;
import spring.practice.elmenus_lite.service.OrderService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;
import spring.practice.elmenus_lite.util.PageInfo;

@RestController
@RequestMapping("/api/v1/orders/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderApiDtoMapper orderApiDtoMapper;

    @GetMapping(path = "{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") Integer orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderDetails(orderId);
        OrderResponseApiDto orderResponseApiDto = orderApiDtoMapper.mapOrderResponseDtoToApiDto(orderResponseDto);
        ApiResponse<?> response = new ApiResponse<>(orderResponseApiDto);
        return ResponseEntity.ok().body(response.getData());
    }


    @GetMapping(path = "customers/{customerId}/orders")
    public ResponseEntity<?> getAllOrders(
            @PathVariable("customerId") Integer customerId,
            @RequestParam(defaultValue = "0" , required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<OrderResponseDto> orderResponseDto = orderService.getAllOrders(customerId , pageable);
        PageInfo page = new PageInfo(
                orderResponseDto.getSize(),
                orderResponseDto.getNumber(),
                (int) orderResponseDto.getTotalElements(),
                orderResponseDto.getTotalPages()
        );
        Page<OrderResponseApiDto> orderResponseApiDtoPage = orderApiDtoMapper.mapToPage(orderResponseDto);
        ApiResponse<?> response = new ApiResponse<>(orderResponseApiDtoPage.getContent(), page);
        return ResponseEntity.ok().body(response);

    }

    @PutMapping("/{orderId}/status/{statusName}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable(name = "orderId") Integer orderId,
            @PathVariable(name = "statusName") String statusName
    ){
        orderService.updateOrderStatus(orderId, statusName);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.ORDER_STATUS_UPDATED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> makeOrder(@Valid @RequestBody OrderApiDto orderApiDto) {
        OrderDto orderDto = orderApiDtoMapper.mapOrderApiDtoToDto(orderApiDto);
        orderService.makeOrder(orderDto);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.ORDER_PLACED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

}
