
package com.haraji.security.service.token;

import com.haraji.security.config.JwtProperties;
import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.RefreshTokenRepository;
import com.haraji.security.exception.authentication.token.RefreshTokenReuseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final String UNKNOWN_DEVICE = "UNKNOWN";

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Override
    public String createRefreshToken(UserEntity user) {
        String token = UUID.randomUUID().toString();

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(user);
        entity.setToken(token);
        entity.setExpireAt(LocalDateTime.now().plusDays(jwtProperties.getRefreshExpirationDays()));
        entity.setRevoked(false);
        entity.setDeviceId(UNKNOWN_DEVICE);

        refreshTokenRepository.save(entity);

        return token;
    }

    @Override
    @Transactional(noRollbackFor = RefreshTokenReuseException.class)
    public RefreshTokenEntity validateRefreshToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Refresh Token الزامی است.");
        }

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByTokenForUpdate(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh Token معتبر نیست."));

        UserEntity user = refreshToken.getUser();

        if (user == null) {
            throw new IllegalStateException("کاربر مرتبط با Refresh Token معتبر نیست.");
        }

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            revokeAll(user.getId());
            throw new RefreshTokenReuseException();
        }

        if (refreshToken.getExpireAt() == null ||
                !refreshToken.getExpireAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh Token منقضی شده است.");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new IllegalStateException("حساب کاربری فعال نیست.");
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new IllegalStateException("حساب کاربری قفل شده است.");
        }

        return refreshToken;
    }

    @Override
    public void revoke(String token) {
        if (token == null || token.isBlank()) {
            return;
        }

        refreshTokenRepository.findByTokenForUpdate(token).ifPresent(refreshToken -> {
            if (!Boolean.TRUE.equals(refreshToken.getRevoked())) {
                refreshToken.setRevoked(true);
            }
        });
    }

    @Override
    public void revokeAll(Long userId) {
        if (userId == null) {
            return;
        }

        refreshTokenRepository.findAllByUserIdAndRevokedFalse(userId)
                .forEach(token -> token.setRevoked(true));
    }
}
