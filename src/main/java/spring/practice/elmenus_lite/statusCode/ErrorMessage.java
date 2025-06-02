package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public enum ErrorMessage {
    ORDER_NOT_FOUND("Order with id: {0} not found"),
    INVALID_ORDER_STATUS("Invalid order status: {0}"),
    INVALID_ORDER_STATUS_TRANSITION("Invalid order status transition: {0}"),
    CART_NOT_FOUND("Cart with id: {0} doesn't exist"),
    EMPTY_CART("Cart with id: {0} doesn't has items"),
    USER_NOT_FOUND("User with id: {0} doesn't exist"),
    MENU_ITEM_IS_NOT_EXIST("Menu Item(s) with [id: {0}, Name: {1}] are not exist");

    private final String messageTemplate;

    public String getFinalMessage(Object... params) {
        return MessageFormat.format(messageTemplate, params);
    }
}
