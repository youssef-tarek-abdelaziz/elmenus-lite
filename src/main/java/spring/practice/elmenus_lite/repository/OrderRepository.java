package spring.practice.elmenus_lite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    Page<OrderModel> findByCustomerId(Integer customerId, Pageable page);
}
