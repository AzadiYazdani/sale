package com.haraji.security.database.repository;

import com.haraji.security.database.entity.RefreshTokenEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    List<RefreshTokenEntity> findAllByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RefreshTokenEntity r WHERE r.token = :token")
    Optional<RefreshTokenEntity> findByTokenForUpdate(@Param("token") String token);

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    void deleteAllByUserId(Long userId);

    void deleteAllByExpireAtBefore(LocalDateTime time);

    List<RefreshTokenEntity> findAllByUserIdAndRevokedFalse(Long userId);
}
