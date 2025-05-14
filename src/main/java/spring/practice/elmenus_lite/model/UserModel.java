package spring.practice.elmenus_lite.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "user_type_id", nullable = false)
//    private UserTypeModel userType;
// Test line

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "last_login")
    private java.time.OffsetDateTime lastLogin;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}

