package com.workforce.tracker.auth.controller;

import com.workforce.tracker.auth.dto.LoginRequestDto;
import com.workforce.tracker.auth.service.AuthService;
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


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto dto){
        return authService.login(dto);
    }
}
