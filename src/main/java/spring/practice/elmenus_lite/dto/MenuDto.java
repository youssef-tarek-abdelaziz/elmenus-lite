package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDto {
   private Integer id;
   private String menuName;
   private String restaurantName;
   private List<MenuItemDto> menuItems;
}
