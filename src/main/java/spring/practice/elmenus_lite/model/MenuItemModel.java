package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class MenuItemModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    private MenuModel menuModel;

    private String menuItemName;
    private Integer price;
    private boolean available;
}
