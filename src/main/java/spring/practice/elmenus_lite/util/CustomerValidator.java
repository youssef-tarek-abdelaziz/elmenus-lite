package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

@Component
@RequiredArgsConstructor
public class CustomerValidator {
    private final CustomerRepository customerRepository;

    public CustomerModel checkCustomerExistence(Integer customerId) {
        return customerRepository.findById(customerId) .orElseThrow(() -> new BadRequestException(ErrorMessage.CUSTOMER_NOT_EXIST.getFinalMessage(customerId)));
    }
}