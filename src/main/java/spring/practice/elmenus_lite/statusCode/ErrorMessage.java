package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessage {
    CART_NOT_FOUND("Cart with id: % doesn't exist"),
    EMPTY_CART("Cart with id: % doesn't has items");

    private final String messageTemplate;
    public String getFinalMessage(Object... params) {
        return String.format(messageTemplate, params);
    }
}
