package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuAPiRequestDto {
    private String menuName;
    private Integer restaurantId;
}
