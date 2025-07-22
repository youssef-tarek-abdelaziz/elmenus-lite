package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.UserDto;
import spring.practice.elmenus_lite.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserModel user);
}
