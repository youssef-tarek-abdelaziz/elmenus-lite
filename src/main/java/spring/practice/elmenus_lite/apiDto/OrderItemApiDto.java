package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemApiDto {
    private Long menuItemId;
    private String menuItemName;
    private int quantity;
    private Integer totalPrice;
    private String restaurantName;
}
