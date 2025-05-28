package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItemModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemModel menuItem;

    private BigDecimal unitPrice;

    private Integer quantity;
}
