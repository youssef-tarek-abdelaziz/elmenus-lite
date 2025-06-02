package spring.practice.elmenus_lite.mapper;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import spring.practice.elmenus_lite.apiDto.AddressApiDto;
import spring.practice.elmenus_lite.dto.AddressDto;
import spring.practice.elmenus_lite.model.AddressModel;

@Mapper(componentModel = "spring")
public interface AddressModelDtoMapper {

    @Mapping(target = "location", ignore = true)
    AddressModel mapAddressApiDtoToDto(AddressDto address);

    @Mapping(target = "location", ignore = true)
    AddressDto toAddressDto(AddressModel address);



}
