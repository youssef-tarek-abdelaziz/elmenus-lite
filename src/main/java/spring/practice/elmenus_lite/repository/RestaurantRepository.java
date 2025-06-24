package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.RestaurantModel;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantModel, Integer> {
    List<RestaurantModel> findByActiveTrue();
}
