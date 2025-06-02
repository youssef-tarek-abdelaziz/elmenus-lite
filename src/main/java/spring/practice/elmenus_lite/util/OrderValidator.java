package spring.practice.elmenus_lite.util;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.dto.OrderValidationSuccessResultDro;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.AddressModel;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.PromotionModel;
import spring.practice.elmenus_lite.repository.AddressRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OrderValidator {
    private final CustomerValidator customerValidator;
    private final PromotionValidator promotionValidator;
    private final MenuItemValidator menuItemValidator;
    private final AddressRepository addressRepository;

    public OrderValidationSuccessResultDro validateOrderInfo(OrderDto orderDto) {
        CustomerModel customerModel = customerValidator.checkCustomerExistence(orderDto.getCustomerId());

        PromotionModel promotionModel = null;
        if (!StringUtil.isNullOrEmpty(orderDto.getPromotionCode())) {
            promotionModel = promotionValidator.checkPromotionAvailability(orderDto.getPromotionCode());
            promotionValidator.isDateWithinPromotionPeriod(orderDto.getOrderDate(), promotionModel);
        }
        List<MenuItemModel> meuItems = menuItemValidator.validateMenuItems(orderDto.getItems().stream().map(OrderItemDto::getMenuItemId).toList());
        menuItemValidator.verifyMenuItemsRestaurantMatch(meuItems);
        AddressModel addressModel = null;
        if(orderDto.getAddress() == null) {
            Optional<AddressModel> addressModelOptional = addressRepository.findByCustomer_id(orderDto.getCustomerId());
            addressModel = addressModelOptional.orElseThrow(() -> new BadRequestException(ErrorMessage.PLACE_ORDER_WITHOUT_ADDRESS.getFinalMessage()));
        }
        return new OrderValidationSuccessResultDro(customerModel, promotionModel, meuItems, addressModel);
    }

}
