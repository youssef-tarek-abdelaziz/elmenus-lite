package spring.practice.elmenus_lite.model;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Table(name = "restaurant_details")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RestaurantDetails extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantModel restaurant;

    @Column(columnDefinition = "text")
    private String description;

    @Size(max = 15)
    private String phone;

    @Column(columnDefinition = "interval")
    @Type(PostgreSQLIntervalType.class)
    private Duration estimatedDeliveryTime;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;
}
