package spring.practice.elmenus_lite.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.practice.elmenus_lite.model.OrderStatusModel;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(excludeAutoConfiguration = org.springframework.data.jpa.repository.config.EnableJpaAuditing.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderStatusRepositoryTest {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Test
    void testFindByOrderStatusName() {
        OrderStatusModel orderStatusModel = new OrderStatusModel();
        orderStatusModel.setOrderStatusName("PENDING");
        orderStatusRepository.save(orderStatusModel);

        Optional<OrderStatusModel> result = orderStatusRepository.findByOrderStatusName("PENDING");

        assertTrue(result.isPresent());
        assertEquals("PENDING", result.get().getOrderStatusName());
    }

}