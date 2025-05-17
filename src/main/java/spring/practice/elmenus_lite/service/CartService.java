package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.repository.CartItemRepository;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public List<CartItemModel> updateCartItems(Integer cartId, List<CartItemModel> cartItems) {
        Optional<CartModel> cartModelOptional = cartRepository.findById(cartId);
        CartModel cartModel = cartModelOptional.orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getMessage()));

//        Set<CartItemModel> oldItems = cartModel.getCartItems();
//        oldItems.addAll(cartItems);
//        oldItems.removeAll(oldItems.stream().filter(oldItem -> oldItem.get))
//        oldItems.stream()
//                .filter(oldItem -> cartItems
//                        .stream()
//                        .noneMatch(newItem -> newItem.getMenuItem().equals(oldItem.getMenuItem())
//                        && oldItem.getQuantity().equals(newItem.getQuantity())))
//                .collect(Collectors.toSet());
        return null;

    }
}
