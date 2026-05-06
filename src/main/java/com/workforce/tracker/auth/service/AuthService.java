package com.workforce.tracker.auth.service;

import com.workforce.tracker.auth.dto.AuthResponse;
import com.workforce.tracker.auth.dto.LoginRequestDto;
import com.workforce.tracker.auth.dto.RefreshRequest;
import com.workforce.tracker.auth.entity.RefreshToken;
import com.workforce.tracker.auth.repository.RefreshTokenRepository;
import com.workforce.tracker.auth.util.JwtUtil;
import com.workforce.tracker.common.exception.ResourceNotFoundException;
import com.workforce.tracker.user.entity.User;
import com.workforce.tracker.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequestDto dto) {

        log.info("Login request started for username: {}", dto.getUsername());

        User user = validateUser(dto);

        String accessToken = generateAccessToken(user);

        RefreshToken refreshToken = createRefreshToken(user);

        log.info("Login successful for username: {}", dto.getUsername());

        return buildAuthResponse(accessToken, refreshToken);
    }

    private User validateUser(LoginRequestDto dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {

            log.warn("Invalid password attempt for username: {}", dto.getUsername());

            throw new ResourceNotFoundException("Invalid password");
        }

        return user;
    }

    private String generateAccessToken(User user) {

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }

    private RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .username(user.getUsername())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    private AuthResponse buildAuthResponse(
            String accessToken,
            RefreshToken refreshToken
    ) {

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse refreshToken(RefreshRequest request) {
        log.info("Refresh token flow started");

        RefreshToken oldRefreshToken= refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));

        if(oldRefreshToken.isRevoked()){
            log.warn("Refresh token is used");

            throw new ResourceNotFoundException("Refresh token revoked");
        }

        if(oldRefreshToken.getExpiryDate().isBefore(Instant.now())){
            log.warn("Expired refresh token used");

            throw new ResourceNotFoundException("Refresh token expired");
        }

        User user= userRepository.findByUsername(oldRefreshToken.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //Revoke old Token
        revokeRefreshToken(oldRefreshToken);

        //create new refresh token
        RefreshToken newRefreshToken = createRefreshToken(user);

        String newAccessToken = generateAccessToken(user);

        log.info("Refresh token flow finished");

        return buildAuthResponse(newAccessToken, newRefreshToken);
    }

    private void revokeRefreshToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}