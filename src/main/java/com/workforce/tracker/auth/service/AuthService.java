package com.workforce.tracker.auth.service;

import com.workforce.tracker.auth.dto.LoginRequestDto;
import com.workforce.tracker.auth.util.JwtUtil;
import com.workforce.tracker.common.exception.ResourceNotFoundException;
import com.workforce.tracker.user.entity.User;
import com.workforce.tracker.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequestDto dto){
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            throw new ResourceNotFoundException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }


}
