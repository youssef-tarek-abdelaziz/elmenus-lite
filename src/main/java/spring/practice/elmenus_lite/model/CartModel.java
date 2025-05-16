package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CartModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.MERGE)
//    @JoinColumn(name = "cart_item_id")
    private List<CartItemModel> cartItemModelList = new ArrayList<>();
}
