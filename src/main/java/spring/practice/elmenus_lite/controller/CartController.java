package spring.practice.elmenus_lite.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.service.CartService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

import java.util.List;
import java.util.Set;

@RestController("/api/cart/")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(path = "{id}/items")
    public ResponseEntity<Set<CartItemModel>> getCartItems(@PathVariable("id") Integer id){
        Set<CartItemModel> cartItems = cartService.getAllItems(id);
        return ResponseEntity.ok().body(cartItems);
    }
    @PutMapping("{id}/items")
    public ResponseEntity<ApiResponse<?>> updateCartItems(@PathVariable("id") Integer cartId, @RequestBody List<CartItemModel> cartItems) {
//        List<CartItemModel> updatedCartItems = cartService.updateCartItems(cartId, cartItems);
//        ApiResponse<List<CartItemModel>> response = new ApiResponse<>(SuccessStatusCode.CART_ITEMS_UPDATED_SUCCESSFULLY.getMessage(), updatedCartItems);
//        return ResponseEntity.ok().body(response);
        return null;
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") Integer cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.ok(SuccessStatusCode.CART_DELETED_SUCCESSFULLY.getFinalMessage());
    }

    @DeleteMapping(path = "{id}/clear")
    public ResponseEntity<String> clearCart(@PathVariable("id") Integer cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Cart deleted successfully");
    }

}
