package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemDto {
    private Integer id;
    private String itemName;
    private Integer price;
    private Boolean available;
}
