package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.dto.MenuItemDto;

import java.util.List;

@Getter
@Setter
public class MenuApiDto {
    private Integer id;
    private String menuName;
    private String restaurantName;
    private List<MenuItemDto> menuItems;
}
