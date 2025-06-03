package spring.practice.elmenus_lite.apiDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderApiDto {
    private Integer id;
    private String restaurantName;
    @NotNull
    private Long customerId;
    private String promotionCode;
    @NotNull
    private List<OrderItemApiDto> items;
    private AddressApiDto address;
    private Integer totalPrice;
    private String orderStatus;


}
