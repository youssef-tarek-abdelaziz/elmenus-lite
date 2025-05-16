package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.locationtech.jts.geom.Point;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @Column(length = 50)
    private String label;

    @Column(nullable = false)
    @NotNull(message = "Provide Street Name")
    private String street;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Provide Floor Number")
    private String floor;

    @Column(length = 50, nullable = false)
    private String apartment;

    @Column(name = "additional_direction", columnDefinition = "TEXT")
    private String direction;

    @Column(length = 50, nullable = false)
    private String state;

    @Column(length = 50, nullable = false)
    private String country;

    @Column( name = "zip_code" , length = 10)
    private String zipCode;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(columnDefinition = "geography(Point, 4326)", nullable = false)
    private Point location;

}
