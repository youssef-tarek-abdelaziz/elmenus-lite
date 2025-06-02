package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import spring.practice.elmenus_lite.apiDto.*;
import spring.practice.elmenus_lite.dto.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderApiDtoMapper {

    default Page<OrderApiDto> mapToPage(Page<OrderDto> data) {
        return data.map(this::mapOrderResponseDtoToApiDto);
    }

    OrderApiDto mapOrderResponseDtoToApiDto(OrderDto orderDto);


    @Mapping(target = "items", expression = "java(mapOrderItemApiDtosToDtos(orderApiDto.getItems()))")
    @Mapping(target = "address", expression = "java(mapAddressApiDtoToDto(orderApiDto.getAddress()))")
    OrderDto mapOrderApiDtoToDto(OrderApiDto orderApiDto);

    List<OrderItemDto> mapOrderItemApiDtosToDtos(List<OrderItemApiDto> orderItemApiDtos);

    OrderItemDto mapOrderItemApiDtoToDto(OrderItemApiDto orderItemDto);

    AddressDto mapAddressApiDtoToDto(AddressApiDto addressApiDto);

}