package com.haraji.security.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token",
        indexes = {@Index(name = "idx_refresh_token", columnList = "token")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    @Column(nullable = false)
    private Boolean revoked = false;

    @Column(nullable = false)
    private String deviceId;

    private String ipAddress;

    private String userAgent;
}
