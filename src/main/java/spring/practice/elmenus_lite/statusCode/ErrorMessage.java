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
    MENU_ITEM_IS_NOT_EXIST("Menu Item(s) with id {0} are not exist"),
    CUSTOMER_NOT_EXIST("Customer with id: {0} is not exist"),
    PROMOTION_CODE_IS_NOT_EXIST("Promotion code: {0} is not not valid"),
    RESTAURANT_IS_NOT_EXIST("Restaurant with id: {0} is not exist"),
    NO_RESTAURANTS_FOUND("No restaurants found"),
    RESTAURANT_DETAILS_NOT_EXIST("Restaurant details with id: {0} is not exist"),
    NOT_SAME_RESTAURANT("An order can only contain items from one restaurant"),
    OUTSIDE_PROMOTION_PERIOD("The provided order date: {0} is outside promotion date range"),
    PLACE_ORDER_WITHOUT_ADDRESS("No address exist, please provide order address"),
    MENU_NOT_FOUND("Menu with id: {0} doesn't exist");
    private final String messageTemplate;

    public String getFinalMessage(Object... params) {
        return MessageFormat.format(messageTemplate, params);
    }
}
