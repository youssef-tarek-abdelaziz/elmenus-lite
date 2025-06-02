package spring.practice.elmenus_lite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.AddressDto;
import spring.practice.elmenus_lite.mapper.AddressModelDtoMapper;
import spring.practice.elmenus_lite.model.AddressModel;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.repository.AddressRepository;
import spring.practice.elmenus_lite.util.CustomerValidator;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerValidator customerValidator;
    private final AddressModelDtoMapper addressModelDtoMapper;
    @Transactional
    public AddressModel addAddress(Integer customerId, AddressDto newAddress) {
        // TODO: future validation: check if the address is duplicated
        CustomerModel customerModel = customerValidator.checkCustomerExistence(customerId);
        AddressModel addressModel = addressModelDtoMapper.mapAddressApiDtoToDto(newAddress);
        addressModel.setCustomer(customerModel);
        return addressRepository.save(addressModel);
    }
}
