package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseApiDto {
    private Integer id;
    private String restaurantName;
    private List<OrderItemResponseApiDto> items;
    private Integer totalPrice;
    private String orderStatus;
    private AddressApiDto address;


}
