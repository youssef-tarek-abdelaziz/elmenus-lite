package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MenuModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantModel restaurantModel;

    private String menuName;
}


/*
    Return Type: Response
    Abdo:
       -- mapstruct
       -- POST /cart/{id}
          Body: cart Items
          validate cart id, if not exist throw exception
    Youssef:
        PUT: /cart/{id}/items
        will be sync
        DELETE: /cart/{id}/clearitems

    Mariam:
        DELETE: /cart
           -- validate cart items size
              --> if > 0: throw exception
              --> else: deleted successfully
        GET: /cart/{id}
 */
