package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @Column(length = 50)
    private String label;

    @Column(nullable = false)
    @NotNull(message = "Provide Street Name")
    private String street;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50)
    @NotNull(message = "Provide Floor Number")
    private String floor;

    @Column(length = 50)
    private String apartment;

    private String additionalDirection;

    @Column(length = 50)
    private String state;

    @Column(length = 50, nullable = false)
    private String country;

    private String zipCode;

    private Boolean isDefault;

    @Column(columnDefinition = "geography(Point, 4326)", nullable = false)
    private Point location;

}
