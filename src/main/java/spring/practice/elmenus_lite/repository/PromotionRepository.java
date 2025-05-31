package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.PromotionModel;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionModel, Integer> {

    Optional<PromotionModel> findByCodeEqualsAndActiveEquals(String promotionCode, boolean active);
}
