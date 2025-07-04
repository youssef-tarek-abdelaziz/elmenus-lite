package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemApiDto {
    private Integer id;
    private String itemName;
    private Integer price;
    private Boolean available;
}
