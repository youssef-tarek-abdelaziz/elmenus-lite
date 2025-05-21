package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public enum ErrorMessage {
    CART_NOT_FOUND("Cart with id: {0} doesn't exist"),
    EMPTY_CART("Cart with id: {0} doesn't has items"),
    USER_NOT_FOUND("User with id: {0} doesn't exist"),
    MENU_ITEM_IS_NOT_EXIST("Menu Item(s) with [id: {0}, Name: {1}] are not exist");

    private final String messageTemplate;

    public String getFinalMessage(Object... params) {
        return MessageFormat.format(messageTemplate, params);
    }
}
