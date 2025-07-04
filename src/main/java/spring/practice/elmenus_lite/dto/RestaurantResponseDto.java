package spring.practice.elmenus_lite.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDto {
    private Integer id;
    private String name;
    private Set<CategoryDto> categories;
    private Boolean active;
    private String description;
    private String phone;
    private Duration estimatedDeliveryTime;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createdAt;
}
