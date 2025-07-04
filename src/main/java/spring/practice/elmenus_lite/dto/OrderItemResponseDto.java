package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private String menuItemName;
    private Integer quantity;
    private Integer totalPrice;
    private String restaurantName;
}
