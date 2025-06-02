package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.practice.elmenus_lite.dto.AddressDto;
import spring.practice.elmenus_lite.model.AddressModel;

@Mapper(componentModel = "spring")
public interface AddressModelDtoMapper {

    @Mapping(target = "location", ignore = true)
    AddressModel mapAddressApiDtoToDto(AddressDto address);
}
