package com.haraji.security.database.repository;

import com.haraji.security.database.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    List<RefreshTokenEntity> findAllByUserId(Long userId);

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    void deleteAllByUserId(Long userId);

    void deleteAllByExpireAtBefore(LocalDateTime time);

    List<RefreshTokenEntity> findAllByUserIdAndRevokedFalse(Long userId);
}
