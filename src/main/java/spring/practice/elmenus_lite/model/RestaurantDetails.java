package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "restaurant_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_details_id", nullable = false)
    private Integer restaurantDetailsId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, foreignKey = @ForeignKey(name = "fk_restaurant_details_restaurant"))
    private Restaurant restaurant;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "estimated_delivery_time")
    private String estimatedDeliveryTime;  // For interval type (you could use a different type if needed)

    @Column(name = "open_time", nullable = false)
    private String openTime;

    @Column(name = "close_time", nullable = false)
    private String closeTime;

    @Column(name = "location", nullable = false)
    private ?? location;  // Assuming you are using PostGIS or a similar spatial extension

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private Timestamp updatedAt;

    @Column(name = "created_by", nullable = false, length = 255)
    private String createdBy;

    @Column(name = "updated_by", nullable = false, length = 255)
    private String updatedBy;
}
