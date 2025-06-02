package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.apiDto.OrderApiDto;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.mapper.OrderApiDtoMapper;
import spring.practice.elmenus_lite.service.OrderService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

@RestController
@RequestMapping("/api/v1/orders/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderApiDtoMapper orderApiDtoMapper;

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
