package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.apiDto.AddressApiDto;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Integer id;
    private String restaurantName;
    private List<OrderItemResponseDto> items;
    private Integer totalPrice;
    private String orderStatus;
    private AddressApiDto address;

}
