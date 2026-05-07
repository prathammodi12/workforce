package com.workforce.tracker.auth.controller;

import com.workforce.tracker.auth.dto.AuthResponse;
import com.workforce.tracker.auth.dto.LoginRequestDto;
import com.workforce.tracker.auth.dto.LogoutRequest;
import com.workforce.tracker.auth.dto.RefreshRequest;
import com.workforce.tracker.auth.service.AuthService;
import com.workforce.tracker.common.response.CommonResponse;
import com.workforce.tracker.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest request){
        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(@RequestBody LogoutRequest request){
        authService.logout(request);

        return CommonResponse.success(null);
    }
}
