package spring.practice.elmenus_lite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResturantDto {
    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotEmpty(message = "At least one category is required")
    private Set<Integer> categoryIds = new HashSet<>();

    private Boolean active = false;

    @Size(max = 500, message = "Description can't exceed 500 characters")
    private String description;

    @Size(max = 15)
    private String phone;

    @NotNull(message = "Estimated delivery time is required")
    private Duration estimatedDeliveryTime;

    @NotNull(message = "Open time is required")
    private LocalTime openTime;

    @NotNull(message = "Open time is required")
    private LocalTime closeTime;

    @NotNull(message = "Location longitude is required")
    private Double longitude;

    @NotNull(message = "Location Latitude is required")
    private Double latitude;
}
