package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.PromotionModel;
import spring.practice.elmenus_lite.repository.PromotionRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PromotionValidator {
    private final PromotionRepository promotionRepository;

    public PromotionModel checkPromotionAvailability(String promotionCode) {
        return promotionRepository.findByCodeEqualsAndActiveEquals(promotionCode, true) .orElseThrow(() -> new BadRequestException(ErrorMessage.PROMOTION_CODE_IS_NOT_EXIST.getFinalMessage(promotionCode)));
    }
    public void isDateWithinPromotionPeriod(LocalDateTime date, PromotionModel promotionModel) {
        boolean isValid =  (promotionModel.getStartAt().isBefore(date) || promotionModel.getStartAt().isEqual(date))
                            &&
                           (promotionModel.getEndAt().isAfter(date) || promotionModel.getEndAt().isEqual(date));
        if(!isValid) {
            throw new BadRequestException(ErrorMessage.OUTSIDE_PROMOTION_PERIOD.getFinalMessage(date));
        }
    }
}