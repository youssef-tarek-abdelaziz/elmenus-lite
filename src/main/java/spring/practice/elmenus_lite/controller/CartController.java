package spring.practice.elmenus_lite.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.apiDto.CartItemRequestApiDto;
import spring.practice.elmenus_lite.apiDto.CartResponseApiDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;
import spring.practice.elmenus_lite.mapper.CartApiDtoMapper;
import spring.practice.elmenus_lite.service.CartService;
import spring.practice.elmenus_lite.statusCode.SuccessStatusCode;
import spring.practice.elmenus_lite.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/cart/")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartApiDtoMapper cartApiDtoMapper;

    @GetMapping("{customerId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Integer customerId) {
        CartResponseDto cartDto = cartService.getCartByUserId(customerId);
        CartResponseApiDto cartResponseApiDto = cartApiDtoMapper.mapCartResponseDtoToApiDto(cartDto);
        ApiResponse<?> response = new ApiResponse<>(cartResponseApiDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "{id}/items")
    public ResponseEntity<?> getCartItems(@PathVariable("id") Integer id){
        CartResponseDto cartResponseDto = cartService.getAllItems(id);
        CartResponseApiDto cartItemResponseApiDtos = cartApiDtoMapper.mapCartResponseDtoToApiDto(cartResponseDto);
        ApiResponse<?> response = new ApiResponse<>(cartItemResponseApiDtos);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("{id}")
    public ResponseEntity<?> addOrUpdateCartItems(@PathVariable("id") Integer cartId, @RequestBody List<CartItemRequestApiDto> cartItemsApiDto){
        List<CartItemDto> cartItemDtos = cartApiDtoMapper.mapCartItemApiDtosToDtos(cartItemsApiDto);
        cartService.addItemsToCart(cartId, cartItemDtos);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.CART_ITEMS_ADDED_UPDATED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "{id}/clear")
    public ResponseEntity<?> clearCart(@PathVariable("id") Integer cartId){
        cartService.clearCart(cartId);
        ApiResponse<?> response = new ApiResponse<>(SuccessStatusCode.CART_ITEM_DELETED_SUCCESSFULLY.getFinalMessage());
        return ResponseEntity.ok().body(response);
    }

}
