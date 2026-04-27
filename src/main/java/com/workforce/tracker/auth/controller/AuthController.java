package com.workforce.tracker.auth.controller;

import com.workforce.tracker.auth.dto.LoginRequestDto;
import com.workforce.tracker.auth.entity.Role;
import com.workforce.tracker.auth.service.AuthService;
import com.workforce.tracker.user.entity.User;
import com.workforce.tracker.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public AuthController(AuthService authService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto dto){
        return authService.login(dto);
    }

    @PostMapping("/create-user")
    public String createUser() {

        User user = new User();
        user.setUsername("admin");

        user.setEmail("admin@gmail.com");

        user.setPassword(passwordEncoder.encode("123"));
        user.setRole(Role.ADMIN);
        user.setIsActive(true);

        userRepository.save(user);

        return "User created successfully";
    }
}
