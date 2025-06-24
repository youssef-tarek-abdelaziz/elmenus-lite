package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Table(name = "review")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Review extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantModel restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    private short rating;

    @Column(columnDefinition = "text")
    private String comment;
}
