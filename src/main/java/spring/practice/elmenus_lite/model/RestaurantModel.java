package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RestaurantModel extends AuditingFields implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String restaurantName;

    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

}
