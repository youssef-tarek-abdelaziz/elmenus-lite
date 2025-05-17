package spring.practice.elmenus_lite.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.service.CartService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

import java.util.List;

@RestController("/api/cart/")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping("{id}/items")
    public ResponseEntity<ApiResponse<?>> updateCartItems(@PathVariable("id") Integer cartId, @RequestBody List<CartItemModel> cartItems) {
        List<CartItemModel> updatedCartItems = cartService.updateCartItems(cartId, cartItems);
        ApiResponse<List<CartItemModel>> response = new ApiResponse<>(SuccessStatusCode.CART_ITEMS_UPDATED_SUCCESSFULLY.getMessage(), updatedCartItems);
        return ResponseEntity.ok().body(response);
    }

}
