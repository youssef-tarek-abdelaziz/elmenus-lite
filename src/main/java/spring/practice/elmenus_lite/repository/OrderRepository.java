package spring.practice.elmenus_lite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.OrderModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    Page<OrderModel> findByCustomerId(Integer customerId, Pageable page);


    @EntityGraph(attributePaths = {
            "orderItems",
            "orderItems.menuItem",
            "orderItems.menuItem.menuModel",
            "orderItems.menuItem.menuModel.restaurantModel",
            "orderStatus",
            "address",
            "customer",
            "promotion",
            "orderTracking"
    })
    Optional<OrderModel> findOrderById(Integer id);



}
