package spring.practice.elmenus_lite.model.embededIds;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleId implements Serializable {
    private Integer userId;
    private Integer roleId;
}