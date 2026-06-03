package com.workforce.tracker.auth.repository;

import com.workforce.tracker.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUsername(String username);

    void deleteByExpiryDateBefore(Instant date);

    void deleteByRevokedTrue();
}
