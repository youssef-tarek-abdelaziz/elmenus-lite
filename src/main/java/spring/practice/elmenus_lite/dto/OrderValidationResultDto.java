package spring.practice.elmenus_lite.dto;

import lombok.Data;
import spring.practice.elmenus_lite.model.CustomerModel;
import spring.practice.elmenus_lite.model.MenuItemModel;
import spring.practice.elmenus_lite.model.PromotionModel;

import java.util.List;

@Data
public class OrderValidationResultDto {
    private final CustomerModel customer;
    private final PromotionModel promotion;
    private final List<MenuItemModel> menuItems;
}
