package com.haraji.security.service.token;

import com.haraji.security.database.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteExpiredRefreshTokens() {
        refreshTokenRepository.deleteAllByExpireAtBefore(LocalDateTime.now());
        log.info("Expired refresh tokens cleanup completed.");
    }
}
