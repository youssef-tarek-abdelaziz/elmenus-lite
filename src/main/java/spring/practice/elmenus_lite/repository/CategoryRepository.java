package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
