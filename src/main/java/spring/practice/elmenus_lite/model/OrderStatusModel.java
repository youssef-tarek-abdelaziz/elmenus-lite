package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.*;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Getter
@Setter
@Table(name = "order_status")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_status_name", length = 50, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
}
