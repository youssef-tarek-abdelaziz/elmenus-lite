package spring.practice.elmenus_lite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.MenuModel;

@Repository
public interface MenuRepository extends JpaRepository<MenuModel, Integer> {
    Page<MenuModel> findByRestaurantModel_Id(Integer restaurantId, Pageable page);

    Integer countByRestaurantModel_Id(Integer restaurantId);

}
