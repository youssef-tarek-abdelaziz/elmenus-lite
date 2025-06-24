package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public enum SuccessStatusCode {
    ORDER_STATUS_UPDATED_SUCCESSFULLY("Order status updated successfully"),
    CART_ITEM_DELETED_SUCCESSFULLY("Cart deleted successfully"),
    CART_ITEMS_ADDED_UPDATED_SUCCESSFULLY("Cart Items added/updated Successfully"),
    ORDER_PLACED_SUCCESSFULLY("Order placed successfully"),
    MENU_ADDED_SUCCESSFULLY("Menu added successfully"),
    MENU_ITEM_ADDED_SUCCESSFULLY("Menu item added successfully"),
    MENU_NAME_UPDATED_SUCCESSFULLY("Menu name updated successfully"),
    MENU_ITEM_UPDATED_SUCCESSFULLY("Menu item updated successfully"),
    MENU_DELETED_SUCCESSFULLY("Menu deleted successfully"),
    MENU_ITEM_DELETED_SUCCESSFULLY("Menu item deleted successfully");

    private final String messageTemplate;

    public String getFinalMessage(Object... params) {
        if(params.length == 0)
            return this.messageTemplate;
        return MessageFormat.format(this.messageTemplate, params);
    }}
