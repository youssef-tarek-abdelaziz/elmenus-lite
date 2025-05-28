package spring.practice.elmenus_lite.util;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.elmenus_lite.dto.OrderDto;
import spring.practice.elmenus_lite.dto.OrderItemDto;
import spring.practice.elmenus_lite.dto.OrderValidationResultDto;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.PromotionModel;
import spring.practice.elmenus_lite.model.RestaurantModel;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderValidator {
    private final CustomerValidator customerValidator;
    private final PromotionValidator promotionValidator;
    private final MenuItemValidator menuItemValidator;

    public OrderValidationResultDto makeOrderValidation(OrderDto orderDto) {
        CustomerModel customerModel = customerValidator.checkCustomerExistence(orderDto.getCustomerId());
        PromotionModel promotionModel = null;
        if (!StringUtil.isNullOrEmpty(orderDto.getPromotionCode())) {
            promotionModel = promotionValidator.checkPromotionAvailability(orderDto.getPromotionCode());
            orderDto.setOrderDate(LocalDateTime.now());
            promotionValidator.isDateWithinPromotionPeriod(orderDto.getOrderDate(), promotionModel);
        }
        List<MenuItemModel> meuItems = menuItemValidator.validateMenuItems(orderDto.getItems().stream().map(OrderItemDto::getMenuItemId).toList());
        menuItemValidator.verifyMenuItemsRestaurantMatch(meuItems);
        return new OrderValidationResultDto(customerModel, promotionModel, meuItems);
    }
}
