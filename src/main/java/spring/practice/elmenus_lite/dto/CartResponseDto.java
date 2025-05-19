package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private Integer id;
    private List<CartItemResponseDto> cartItemDtoList;
    private Integer totalPrice;
}
