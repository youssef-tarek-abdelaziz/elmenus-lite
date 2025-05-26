package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
public class OrderModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer is required")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull(message = "Delivery address is required")
    private AddressModel address;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    @NotNull(message = "Order status is required")
    private OrderStatusModel orderStatus;

    @OneToOne
    @JoinColumn(name = "order_tracking_id")
    private OrderTrackingModel orderTracking;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionModel promotion;

    @DecimalMin(value = "0.00" , message = "Discount amount cannot be negative")
    private BigDecimal discountAmount;

    private BigDecimal subtotal;

    private BigDecimal total;

    @Column(insertable = false, updatable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItemModel> orderItems = new ArrayList<>();

}
