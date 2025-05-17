package spring.practice.elmenus_lite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dtos.AddItemsToCartRequest;
import spring.practice.elmenus_lite.service.CartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

   private final CartService cartService;

    @PostMapping("/{cartId}")
    public ResponseEntity<?> addItemsToCart(
            @PathVariable Integer cartId,
            @RequestBody AddItemsToCartRequest request
            ){
        return cartService.addItemsToCart(cartId, request);
    }
}
