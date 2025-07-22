package spring.practice.elmenus_lite.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.practice.elmenus_lite.config.JwtConfig;
import spring.practice.elmenus_lite.dto.*;
import spring.practice.elmenus_lite.mapper.UserMapper;
import spring.practice.elmenus_lite.model.*;
import spring.practice.elmenus_lite.repository.UserRepository;
import spring.practice.elmenus_lite.repository.UserRoleRepository;
import spring.practice.elmenus_lite.service.AuthService;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.service.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final UserRoleRepository userRoleRepository;

    @GetMapping("/user")
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("get the user!");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto request,
                                             HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserModel user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        UserRoleModel role = userRoleRepository.findRoleIdByUserId(user.getId());
        Role roleName = role.getRole().getRoleName();

        String accessToken = jwtService.generateAccessToken(user,roleName);
        String refreshToken = jwtService.generateRefreshToken(user,roleName);

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); //7days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userId = jwtService.getIdFromToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        UserRoleModel role = userRoleRepository.findRoleIdByUserId(user.getId());
        Role roleName = role.getRole().getRoleName();

        var accessToken = jwtService.generateAccessToken(user,roleName);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        UserModel user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = userMapper.toUserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<Void>  handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
