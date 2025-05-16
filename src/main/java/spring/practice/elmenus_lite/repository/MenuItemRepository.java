package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.MenuItemModel;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemModel, Integer> {
}
