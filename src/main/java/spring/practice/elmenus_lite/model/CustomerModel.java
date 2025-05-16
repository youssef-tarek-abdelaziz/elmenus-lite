package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserModel user;

    @Column(length = 15)
    private String phone;

    /**
     * Gender codes based on ISO/IEC 5218:
     * 0 = Unspecified, 1 = Male, 2 = Female
     */
    private Integer gender;

}

