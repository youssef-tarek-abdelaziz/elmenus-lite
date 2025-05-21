package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseApiDto {
    private Integer menuItemId;
    private String menuItemName;
    private Integer quantity;
    private Integer totalPrice;
}
