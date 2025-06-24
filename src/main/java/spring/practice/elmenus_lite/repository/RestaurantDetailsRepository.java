package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.model.RestaurantDetails;
import spring.practice.elmenus_lite.model.RestaurantModel;

import java.util.Optional;

public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Integer> {
    Optional<RestaurantDetails> findByRestaurant(RestaurantModel restaurant);

    void deleteByRestaurant(RestaurantModel restaurant);
}
