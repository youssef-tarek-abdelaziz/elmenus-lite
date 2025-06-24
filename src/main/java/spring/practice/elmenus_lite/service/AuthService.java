package spring.practice.elmenus_lite.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.RegisterDto;
import spring.practice.elmenus_lite.exception.BadRequestException;
import spring.practice.elmenus_lite.exception.EntityNotFoundException;
import spring.practice.elmenus_lite.model.UserModel;
import spring.practice.elmenus_lite.model.UserTypeModel;
import spring.practice.elmenus_lite.repository.UserRepository;
import spring.practice.elmenus_lite.repository.UserTypeRepository;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public void register(RegisterDto request){
        if(userRepository.existsByEmail(request.getEmail())){
            //TODO CUSTOM EXCEPTION HANDLER
            throw new BadRequestException("Email already used");
        }

        //TODO CUSTOM EXCEPTION HANDLER
        UserTypeModel userType = userTypeRepository.findById(request.getUserTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid user type"));

        UserModel user = new UserModel();
        user.setEmail(request.getEmail());
        //TODO PASSWORD ENCODER
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(request.getFullName());
        user.setUserType(userType);
        user.setEnabled(true);

        userRepository.save(user);
    }

}
