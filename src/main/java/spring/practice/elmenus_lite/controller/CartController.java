package spring.practice.elmenus_lite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.service.CartService;

import java.util.List;


@RestController
@RequestMapping(path = "api/elmenus")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }



    @GetMapping("/carts")
    public List<CartModel> getAllCarts() {
        return cartService.getAllCarts();
    }

//    @GetMapping("/carts")
//    public ResponseEntity<?> getAllCarts() {
//        try {
//            List<CartModel> carts = cartService.getAllCarts();
//            if (carts.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Carts Found");
//            }
//            return ResponseEntity.ok(carts);
//        } catch (Exception e) {
//            e.printStackTrace(); // سيطبع الخطأ في الـ Terminal/Console
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Internal Error: " + e.getMessage());
//        }
//    }







// GET
//    @GetMapping(path = "/cart/{id}")
//    public List<CartItemModel> getAllItems(@PathVariable("id") Integer id){
//        return  cartService.getAllItems(id);
//    }


    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCart(@PathVariable("id") Long cartId) {
        try {
            List<CartItemModel> cart = cartService.getAllItems(cartId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart not found");
        }
    }



// DELETE
//    @DeleteMapping(path = "/cart/{id}")
//    public void deleteCart(@PathVariable("id") Long id){
//
//        cartService.deleteCart(id);
//    }

    @DeleteMapping(path = "/cart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") Long id){
        cartService.deleteCart(id);
        return ResponseEntity.ok("Cart deleted successfully");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
