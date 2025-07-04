package spring.practice.elmenus_lite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.practice.elmenus_lite.constants.OrderStatus;
import spring.practice.elmenus_lite.model.OrderModel;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.OrderRepository;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;
import spring.practice.elmenus_lite.util.OrderUtility;
import spring.practice.elmenus_lite.util.OrderValidator;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private OrderValidator orderValidator;
    @Mock
    private OrderUtility orderUtility;

    @InjectMocks
    private OrderService orderService;

    private OrderModel order;
    private OrderStatusModel currentStatus;
    private OrderStatusModel newStatus;

    @BeforeEach
    void setUp() {
        currentStatus = new OrderStatusModel();
        currentStatus.setOrderStatusName(OrderStatus.PENDING);

        newStatus = new OrderStatusModel();
        newStatus.setOrderStatusName(OrderStatus.CANCELLED);

        order = new OrderModel();
        order.setId(1);
        order.setOrderStatus(currentStatus);
    }


}