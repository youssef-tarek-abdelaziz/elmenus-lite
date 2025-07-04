package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public enum SuccessStatusCode {
    ORDER_STATUS_UPDATED_SUCCESSFULLY("Order status updated successfully"),
    CART_ITEM_DELETED_SUCCESSFULLY("Cart deleted successfully"),
    CART_ITEMS_ADDED_UPDATED_SUCCESSFULLY("Cart Items added/updated Successfully"),
    ORDER_PLACED_SUCCESSFULLY("Order placed successfully"),
    RESTAURANT_DELETED_SUCCESSFULLY("Restaurant deleted successfully"),
    RESTAURANT_CREATED_SUCCESSFULLY("Restaurant created successfully"),
    RESTAURANT_UPDATED_SUCCESSFULLY("Restaurant updated successfully");

    private final String messageTemplate;

    public String getFinalMessage(Object... params) {
        if (params.length == 0)
            return this.messageTemplate;
        return MessageFormat.format(this.messageTemplate, params);
    }
}
