package spring.practice.elmenus_lite.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.practice.elmenus_lite.service.OrderService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/status/{statusName}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable(name = "orderId") Integer orderId,
            @PathVariable(name = "statusName") String statusName
    ){
        orderService.updateOrderStatus(orderId, statusName);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.ORDER_STATUS_UPDATED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }
}
