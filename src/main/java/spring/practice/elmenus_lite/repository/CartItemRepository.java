package spring.practice.elmenus_lite.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.CartItemModel;
import spring.practice.elmenus_lite.model.CartModel;

import java.util.List;

@Repository
public interface  CartItemRepository extends JpaRepository<CartItemModel, Integer> {
    List<CartItemModel> findByCartId(Long cartId);
    Integer countByCart(CartModel cartModel);
}
