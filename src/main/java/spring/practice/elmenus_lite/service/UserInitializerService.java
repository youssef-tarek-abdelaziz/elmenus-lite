//package spring.practice.elmenus_lite.service;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import spring.practice.elmenus_lite.model.*;
//import spring.practice.elmenus_lite.model.embededIds.UserRoleId;
//import spring.practice.elmenus_lite.repository.RoleRepository;
//import spring.practice.elmenus_lite.repository.UserRepository;
//import spring.practice.elmenus_lite.repository.UserRoleRepository;
//import spring.practice.elmenus_lite.repository.UserTypeRepository;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Set;
//
//
//@Service
//@RequiredArgsConstructor
//public class UserInitializerService {
//
//    private final RoleRepository roleRepository;
//    private final UserTypeRepository userTypeRepository;
//    private final UserRepository userRepository;
//    private final UserRoleRepository userRoleRepository;
//
//
//    @PostConstruct
//    @Transactional
//    private void init() {
//
//
//        // 1. Add or fetch UserType
//        UserTypeModel userType = userTypeRepository.findByUserTypeName("customer")
//                .orElseGet(() -> {
//                    UserTypeModel type = new UserTypeModel();
//                    type.setUserTypeName("customer");
//                    return userTypeRepository.save(type);
//                });
//
//        // 2. Add or fetch Role
//        RoleModel role = roleRepository.findByRoleName("USER")
//                .orElseGet(() -> {
//                    RoleModel newRole = new RoleModel();
//                    newRole.setRoleName("USER");
//                    return roleRepository.save(newRole);
//                });
//
//
//
//        // 3. Add User if email not taken
//        String email = "user@gmail.com";
//        UserModel user = userRepository.findByEmail(email)
//                .orElseGet(() -> {
//                    UserModel newUser = new UserModel();
//                    newUser.setFirstName("Ali");
//                    newUser.setLastName("Sami");
//                    newUser.setFullName("Ali Sami");
//                    newUser.setEmail(email);
//                    newUser.setEnabled(true);
//                    newUser.setPassword("iyiuoi");
//                    newUser.setUserType(userType);
//                    return userRepository.save(newUser);
//                });
//
//        UserRoleModel userRoleModel = new UserRoleModel();
//        userRoleModel.setId(new UserRoleId(user.getId(), role.getId()));
//        userRoleRepository.save(userRoleModel);
//    }
//
//}
