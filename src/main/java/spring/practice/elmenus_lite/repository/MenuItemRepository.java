package spring.practice.elmenus_lite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemModel, Integer> {
    List<MenuItemModel> findByIdIn(List<Integer> menuItemsIds);

    List<MenuItemModel> findAllByMenuModelId(Integer menuId);

    Page<MenuItemModel> findByMenuModelId(Integer menuId, Pageable pageable);

    List<MenuItemModel> findByMenuModelId(Integer menuId);

    Integer countByMenuModel_Id(Integer menuId);
}
