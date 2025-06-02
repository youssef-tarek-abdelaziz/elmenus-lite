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

    @Column(length = 50, unique = true)
    private String orderStatusName;

    @ManyToOne
    @JoinColumn(name = "previous_status_id")
    private OrderStatusModel previousStatus;
}
