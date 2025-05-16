package spring.practice.elmenus_lite.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.repository.RoleRepository;
import spring.practice.elmenus_lite.repository.UserRepository;
import spring.practice.elmenus_lite.repository.UserRoleRepository;
import spring.practice.elmenus_lite.repository.UserTypeRepository;

import java.time.LocalDateTime;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class UserInitializerService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;


    @PostConstruct
    private void init() {

        // 1. Add or fetch UserType
        UserTypeModel userType = userTypeRepository.findByUserTypeName("customer")
                .orElseGet(() -> {
                    UserTypeModel type = new UserTypeModel();
                    type.setUserTypeName("customer");
                    type.setCreatedBy("system");
                    type.setUpdatedBy("system");
                    type.setCreatedAt(LocalDateTime.now());
                    type.setUpdatedAt(LocalDateTime.now());
                    return userTypeRepository.save(type);
                });

        // 2. Add or fetch Role
        RoleModel role = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    RoleModel newRole = new RoleModel();
                    newRole.setCreatedAt(LocalDateTime.now());
                    newRole.setUpdatedAt(LocalDateTime.now());
                    newRole.setCreatedBy("System");
                    newRole.setUpdatedBy("System");
                    newRole.setRoleName("USER");
                    newRole.setPermissions(Arrays.asList("READ", "WRITE", "DELETE"));
                    return roleRepository.save(newRole);
                });

        // 3. Add User if email not taken
        String email = "user@gmail.com";
        UserModel user = userRepository.findUserByEmail(email)
                .orElseGet(() -> {
                    UserModel newUser = new UserModel();
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    newUser.setCreatedBy("System");
                    newUser.setUpdatedBy("System");
                    newUser.setFirstName("Ali");
                    newUser.setLastName("Sami");
                    newUser.setFullName("Ali Sami");
                    newUser.setEmail(email);
                    newUser.setEnabled(true);
                    newUser.setPassword("iyiuoi");
                    newUser.setUserType(userType);
                    return userRepository.save(newUser);
                });

        // 4. Add UserRole if not exists
        UserRoleId userRoleId = new UserRoleId(user.getUserId(), role.getRoleId());
        boolean roleExists = userRoleRepository.existsById(userRoleId);

        if (!roleExists) {
            UserRoleModel userRole = new UserRoleModel();
            userRole.setId(userRoleId);
            userRole.setUserModel(user);
            userRole.setRoleModel(role);
            userRoleRepository.save(userRole);
        } else {
            System.out.println("User already has this Role");
        }





    }







}
