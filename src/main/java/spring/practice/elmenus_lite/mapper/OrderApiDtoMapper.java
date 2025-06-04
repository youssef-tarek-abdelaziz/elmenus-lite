package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import spring.practice.elmenus_lite.apiDto.*;
import spring.practice.elmenus_lite.dto.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderApiDtoMapper {

    @Mapping(target = "items", expression = "java(mapOrderItemApiDtosToDtos(orderApiDto.getItems()))")
    @Mapping(target = "address", expression = "java(mapAddressApiDtoToDto(orderApiDto.getAddress()))")
    OrderDto mapOrderApiDtoToDto(OrderApiDto orderApiDto);

    List<OrderItemDto> mapOrderItemApiDtosToDtos(List<OrderItemApiDto> orderItemApiDtos);

    OrderItemDto mapOrderItemApiDtoToDto(OrderItemApiDto orderItemDto);

    AddressDto mapAddressApiDtoToDto(AddressApiDto addressApiDto);

    default Page<OrderResponseApiDto> mapToPage(Page<OrderResponseDto> data) {
        return data.map(this::mapOrderResponseDtoToApiDto);
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "restaurantName", source = "restaurantName")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "address", source = "address")
    OrderResponseApiDto mapOrderResponseDtoToApiDto(OrderResponseDto orderDto);

    OrderResponseDto mapOrderResponseApiDtoToDto(OrderDto orderDto);



}