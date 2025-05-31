package spring.practice.elmenus_lite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.PromotionModel;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PromotionService {

    @Transactional
    public BigDecimal getPromotionDiscount(PromotionModel promotionModel, BigDecimal totalPrice) {
        if(promotionModel == null)
            return BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.valueOf(promotionModel.getDiscountPercent().doubleValue() / 100 ).multiply(totalPrice);
        if(discountAmount.compareTo(promotionModel.getMaxDiscount()) > 0) {
            discountAmount = promotionModel.getMaxDiscount();
        }
        return discountAmount;
    }
}
