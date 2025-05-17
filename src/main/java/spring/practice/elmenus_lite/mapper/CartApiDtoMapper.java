package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.apiDto.CartItemRequestApiDto;
import spring.practice.elmenus_lite.dto.CartItemDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartApiDtoMapper {

    CartItemDto mapCartItemApiDtoToDto(CartItemRequestApiDto cartItemRequestApiDto);

    List<CartItemDto> mapCartItemApiDtosToDtos(List<CartItemRequestApiDto> cartItemRequestApiDtos);
}