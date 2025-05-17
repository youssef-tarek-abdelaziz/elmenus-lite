package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum ErrorMessage {
    CART_NOT_FOUND("Cart with id: %s doesn't exist"),
    EMPTY_CART("Cart with id: %s doesn't has items"),
    MENU_ITEM_IS_NOT_EXIST("Menu Item(s) with id(s): %s are not exist");

    private final String messageTemplate;
    public String getFinalMessage(List<Integer> ids) {
        return String.format(messageTemplate, ids.toString());
    }
}
