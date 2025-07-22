package spring.practice.elmenus_lite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.model.auditing.AuditingFields;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class UserModel extends AuditingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserTypeModel userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<UserRoleModel> userRoles = new HashSet<>();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String fullName;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private Boolean enabled = true;
}

