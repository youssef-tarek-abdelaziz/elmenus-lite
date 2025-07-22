package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.UserRoleModel;
import spring.practice.elmenus_lite.model.embededIds.UserRoleId;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleId> {
    UserRoleModel findRoleIdByUserId(Integer userId);

    // Find all users with a specific role
    List<UserRoleModel> findByRoleId(Long roleId);

    // Optional: delete roles for a specific user
    void deleteByUserId(Long userId);

    // Optional: delete users with a specific role
    void deleteByRoleId(Long roleId);
}
