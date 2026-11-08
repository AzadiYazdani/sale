package com.haraji.security.database.entity;

import com.haraji.baseinfo.database.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "sec_refresh_token",
        indexes = {
                @Index(name = "idx_refresh_token_user", columnList = "user_id"),
                @Index(name = "idx_refresh_token_expire", columnList = "expire_at"),
                @Index(name = "idx_refresh_token_user_revoked", columnList = "user_id, revoked")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    @Column(nullable = false)
    private Boolean revoked = false;

    @Column(length = 100)
    private String deviceId;

    private String ipAddress;

    private String userAgent;
}
