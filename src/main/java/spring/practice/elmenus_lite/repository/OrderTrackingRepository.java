package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.OrderTrackingModel;

@Repository
public interface OrderTrackingRepository extends JpaRepository<OrderTrackingModel, Integer> {
}
