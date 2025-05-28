package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Getter
@Setter
@Table(name = "order_status")
@Entity
public class OrderStatusModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String orderStatusName;

    @OneToOne(mappedBy = "orderStatus")
    private OrderModel order;

    public OrderStatusModel(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
