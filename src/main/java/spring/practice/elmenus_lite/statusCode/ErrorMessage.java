package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    CART_NOT_FOUND("Cart with id: % doesn't exist");

    private final String message;
}
