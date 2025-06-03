package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Integer menuItemId;
    private String menuItemName;
    private int quantity;
    private String restaurantName;
    private Integer totalPrice;
}
