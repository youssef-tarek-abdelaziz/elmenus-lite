package spring.practice.elmenus_lite.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatusCode {
    CART_DELETED_SUCCESSFULLY("Cart deleted successfully"),
    CART_ITEMS_UPDATED_SUCCESSFULLY("Cart Items Updated Successfully");

    private final String messageTemplate;
    public String getFinalMessage(Object... params) {
        if(params.length == 0)
            return this.messageTemplate;
        return String.format(this.messageTemplate, params);
    }}
