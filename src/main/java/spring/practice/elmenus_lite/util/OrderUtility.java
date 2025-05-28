package spring.practice.elmenus_lite.util;

import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.PromotionModel;

import java.math.BigDecimal;

@Component
public class OrderUtility {
    public OrderModel calculateOrderPrice(OrderModel orderModel, PromotionModel promotionModel) {
        BigDecimal totalPrice = orderModel.getOrderItems()
                .stream()
                .map(orderItem -> orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())) )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        double discountAmount = (promotionModel.getDiscountPercent().doubleValue() / 100 ) * totalPrice.doubleValue();
        if(discountAmount > promotionModel.getMaxDiscount().doubleValue()) {
            discountAmount = promotionModel.getMaxDiscount().doubleValue();
        }
        orderModel.setTotal(BigDecimal.valueOf(totalPrice.doubleValue() - discountAmount));
        orderModel.setDiscountAmount(BigDecimal.valueOf(discountAmount));
        return orderModel;
    }
}
