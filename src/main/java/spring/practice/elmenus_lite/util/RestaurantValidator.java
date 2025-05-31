package spring.practice.elmenus_lite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.model.RestaurantModel;
import spring.practice.elmenus_lite.repository.RestaurantRepository;
import spring.practice.elmenus_lite.statusCode.ErrorMessage;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {
    private RestaurantRepository restaurantRepository;

    public RestaurantModel validateRestaurantExistence(Integer id) {
        Optional<RestaurantModel> restaurantModel = restaurantRepository.findById(id);
        return restaurantModel.orElseThrow(() -> new BadRequestException(ErrorMessage.RESTAURANT_IS_NOT_EXIST.getFinalMessage(id)));
    }
}
