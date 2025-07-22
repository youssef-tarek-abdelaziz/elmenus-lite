package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.model.Role;
import spring.practice.elmenus_lite.model.RoleModel;

import java.util.Optional;

public interface RoleModelRepository extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByRoleName(Role roleName);
}