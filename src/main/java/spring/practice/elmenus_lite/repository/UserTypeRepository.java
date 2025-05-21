package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.UserTypeModel;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeModel, Integer> {
    Optional<UserTypeModel> findByUserTypeName(String userTypeName);
}
