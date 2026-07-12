package com.haraji.security.service;

import com.haraji.security.config.JwtProperties;
import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProperties jwtProperties;

    @Override
    public String createRefreshToken(UserEntity user) {

        String token = UUID.randomUUID().toString();
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(user);
        entity.setToken(token);
        entity.setExpireAt(
                LocalDateTime.now()
                        .plusDays( jwtProperties.getRefreshExpirationDays())
        );

        entity.setRevoked(false);
        entity.setDeviceId("UNKNOWN");
        refreshTokenRepository.save(entity);
        return token;
    }

    @Override
    public RefreshTokenEntity validateRefreshToken(String token) {

        RefreshTokenEntity refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh token not found"));


        if (refreshToken.getRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (refreshToken.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        if (refreshToken.getUser().getLocked()) {
            throw new RuntimeException("User locked");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void revoke(String token) {
        RefreshTokenEntity refreshToken =validateRefreshToken(token);
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void revokeAll(Long userId) {
        refreshTokenRepository
                .findAllByUserId(userId)
                .forEach(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });

    }
}
