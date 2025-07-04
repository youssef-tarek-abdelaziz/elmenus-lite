package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.OrderItemModel;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, Integer> {
    List<OrderItemModel> findAllByMenuItemId(Integer id);

    OrderItemModel findByMenuItemId(Integer menuItemId);

    boolean existsByMenuItemId(Integer menuItemId);
}
