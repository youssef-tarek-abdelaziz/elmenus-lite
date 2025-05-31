package spring.practice.elmenus_lite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.OrderStatusModel;
import spring.practice.elmenus_lite.repository.OrderStatusRepository;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

   private final OrderStatusRepository orderStatusRepository;

   public OrderStatusModel findInitialOrderStatus() {
       // TODO: will be changed after define status_order column in the table
       return orderStatusRepository.findAll().get(0);
   }
}
