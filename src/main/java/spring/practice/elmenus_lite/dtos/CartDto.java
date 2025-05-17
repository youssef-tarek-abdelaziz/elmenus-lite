package spring.practice.elmenus_lite.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
//    private Integer id;
    private List<CartItemDto> items = new ArrayList<>();
//    private Integer totalPrice;
}
