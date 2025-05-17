package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseApiDto {
    private Integer id;
    private List<CartItemResponseApiDto> cartItemApiDtoList;
    private Integer totalPrice;
}
