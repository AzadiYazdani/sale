package com.haraji.security.service;

import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(UserEntity user);

    RefreshTokenEntity validateRefreshToken(String token);

    void revoke(String token);

    void revokeAll(Long userId);

}
