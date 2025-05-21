package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseDto {
    private Integer menuItemId;
    private String menuItemName;
    private Integer quantity;
    private Integer totalPrice;
}
