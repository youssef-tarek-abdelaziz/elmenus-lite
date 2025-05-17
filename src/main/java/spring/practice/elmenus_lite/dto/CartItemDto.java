package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Integer id;
    private Integer menuItemId;
    private Integer quantity;
}
