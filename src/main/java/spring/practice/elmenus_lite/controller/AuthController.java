package spring.practice.elmenus_lite.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.practice.elmenus_lite.dto.RegisterDto;
import spring.practice.elmenus_lite.dto.LoginDto;
import spring.practice.elmenus_lite.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/user")
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("get the user!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginDto request){
      return ResponseEntity.ok(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

}
