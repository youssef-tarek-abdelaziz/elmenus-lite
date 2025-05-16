package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
public class CartItemModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @ToString.Exclude
    private CartModel cart;

//  TODO: after ading MenuItemModel
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "menu_item_id", nullable = false)
//    private MenuItemModel menuItem;



    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}

