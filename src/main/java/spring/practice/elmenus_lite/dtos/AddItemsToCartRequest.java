package spring.practice.elmenus_lite.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddItemsToCartRequest {
    private List<Integer> items= new ArrayList<>();
}
