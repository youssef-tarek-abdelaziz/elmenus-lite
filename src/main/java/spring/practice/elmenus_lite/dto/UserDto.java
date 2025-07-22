package spring.practice.elmenus_lite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    public Long id;
    public String fullName;
    public String email;
}
