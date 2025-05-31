package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Integer customerId;
    private String promotionCode;
    private List<OrderItemDto> items;
    private AddressDto address;
    private LocalDateTime OrderDate;
}
