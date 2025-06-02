package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.practice.elmenus_lite.model.AddressModel;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Integer> {
    Optional<AddressModel> findByCustomer_id(Integer id);
}
