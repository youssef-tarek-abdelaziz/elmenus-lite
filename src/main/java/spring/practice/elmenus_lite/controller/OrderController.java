package spring.practice.elmenus_lite.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.practice.elmenus_lite.model.OrderStatusEnum;
import spring.practice.elmenus_lite.service.OrderService;

@Controller
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/status/{statusId}")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable(name = "orderId") Integer orderId,
            @PathVariable(name = "statusId") Integer statusId
    ){
        orderService.updateOrderStatus(orderId, statusId);
        return ResponseEntity.noContent().build();
    }
}
