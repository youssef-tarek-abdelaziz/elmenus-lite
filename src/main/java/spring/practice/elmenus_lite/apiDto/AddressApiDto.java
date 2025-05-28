package spring.practice.elmenus_lite.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressApiDto {
    private String street;
    private String city;
    private String floor;
    private String apartment;
    private String country;
    private String state;
    private String location;
    private boolean isDefault;
}
