package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuItemApiRequestDto {
    private Integer menuId;
    private String itemName;
    private Integer price;
}
