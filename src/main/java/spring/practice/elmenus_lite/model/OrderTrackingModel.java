package spring.practice.elmenus_lite.model;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.time.Duration;

@Getter
@Setter
@Entity
@Table(name = "order_tracking")
@NoArgsConstructor
public class OrderTrackingModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point currentLocation;

    @Column(columnDefinition = "interval")
    @Type(PostgreSQLIntervalType.class)
    private Duration estimatedTime;

    @OneToOne(mappedBy = "orderTracking", fetch = FetchType.LAZY)
    private OrderModel orderModel;

    public OrderTrackingModel(Duration estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
