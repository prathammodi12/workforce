package com.workforce.tracker.auth.scheduler;

import com.workforce.tracker.auth.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {

    private final AuthService authService;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void cleanupTokens(){
        log.info("Refresh Token cleanup started");

        authService.cleanupRefreshTokens();

        log.info("Refresh Token cleanup completed");
    }
}
