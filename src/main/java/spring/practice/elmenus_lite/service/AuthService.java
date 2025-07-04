package spring.practice.elmenus_lite.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.LoginDto;
import spring.practice.elmenus_lite.dto.LoginResponseDto;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterDto request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already used");
        }

        UserTypeModel userType = userTypeRepository.findById(request.getUserTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid user type"));

        UserModel user = new UserModel();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(request.getFullName());
        user.setUserType(userType);
        user.setEnabled(true);

        userRepository.save(user);
    }


    public LoginResponseDto login(LoginDto request) {
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponseDto(token);
    }


}
