package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.model.CartModel;
import spring.practice.elmenus_lite.repository.CartRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartValidator {
    private final CartRepository cartRepository;

    public CartModel validateCartForSpecificCustomer(Integer customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND.getFinalMessage(List.of(customerId))));
    }
    public CartModel validateCartExistence(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.CART_NOT_FOUND.getFinalMessage(List.of(id))));
    }
}
