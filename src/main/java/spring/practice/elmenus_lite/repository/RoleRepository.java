package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.RoleModel;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

    Optional<RoleModel> findByRoleName(String roleName);

}
