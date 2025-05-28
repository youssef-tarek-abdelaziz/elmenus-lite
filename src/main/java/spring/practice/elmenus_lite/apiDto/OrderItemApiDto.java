package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemApiDto {
    private Long menuItemId;
    private int quantity;
}
