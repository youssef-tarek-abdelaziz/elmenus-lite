package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.apiDto.CartItemRequestApiDto;
import spring.practice.elmenus_lite.apiDto.CartItemResponseApiDto;
import spring.practice.elmenus_lite.apiDto.CartResponseApiDto;
import spring.practice.elmenus_lite.dto.CartItemDto;
import spring.practice.elmenus_lite.dto.CartItemResponseDto;
import spring.practice.elmenus_lite.dto.CartResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartApiDtoMapper {

    CartItemDto mapCartItemApiDtoToDto(CartItemRequestApiDto cartItemRequestApiDto);

    List<CartItemDto> mapCartItemApiDtosToDtos(List<CartItemRequestApiDto> cartItemRequestApiDtos);

    CartItemResponseApiDto mapCartItemResponseDtoToApiDto(CartItemResponseDto cartItemResponseDto);

    List<CartItemResponseApiDto> mapCartItemResponseDtosToApiDtos(List<CartItemResponseDto> cartItemResponseDto);

    @Mapping(target = "cartItemApiDtoList", expression = "java(mapCartItemResponseDtosToApiDtos(cartResponseDto.getCartItemDtoList()))")
    CartResponseApiDto mapCartResponseDtoToApiDto(CartResponseDto cartResponseDto);

}