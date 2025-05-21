package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestApiDto {
    private Integer id;
    private Integer menuItemId;
    private Integer quantity;
}
