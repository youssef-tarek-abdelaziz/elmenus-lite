package spring.practice.elmenus_lite.dtos;

import lombok.Data;

@Data
public class CartItemDto {
    private MenuItemDto menuItem;
    private int quantity;
    private int totalPrice;
}
