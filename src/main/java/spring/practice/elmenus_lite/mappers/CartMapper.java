package spring.practice.elmenus_lite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dtos.CartItemDto;
import spring.practice.elmenus_lite.model.CartItemModel;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice" , expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartItemDto(CartItemModel cartItem);
}
