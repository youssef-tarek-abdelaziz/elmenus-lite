package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.time.Duration;

@Getter
@Setter
@Entity
@Table(name = "order_tracking")
@Builder
public class OrderTrackingModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point currentLocation;

    private Duration estimatedTime;
}
