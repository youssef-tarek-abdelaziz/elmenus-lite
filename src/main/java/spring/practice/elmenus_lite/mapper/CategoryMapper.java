package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.CategoryDto;
import spring.practice.elmenus_lite.model.Category;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Set<CategoryDto> toCategoriesDtos(Set<Category> categories);
}
