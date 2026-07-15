package com.haraji.security.service;

import com.haraji.security.api.dto.login.LoginPasswordRequest;
import com.haraji.security.api.dto.otp.OtpRequest;
import com.haraji.security.api.dto.otp.VerifyOtpRequest;
import com.haraji.security.api.dto.token.RefreshTokenRequest;
import com.haraji.security.api.dto.token.TokenResponse;
import com.haraji.security.config.JwtProperties;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.UserIdentifierRepository;
import com.haraji.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {

    private final RefreshTokenService refreshTokenService;
    private final UserIdentifierRepository userIdentifierRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;


    @Override
    @Transactional
    public TokenResponse login(LoginPasswordRequest request) {

        UserIdentifierEntity identifier =
                userIdentifierRepository
                        .findByTypeAndValueAndEnabledTrue(
                                request.getIdentifierType(),
                                request.getIdentifier()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Identifier not found"));

        UserEntity user = identifier.getUser();

        if (!user.getEnabled()) {
            throw new RuntimeException("User is not activated");
        }

        if (user.getLocked()) {
            throw new RuntimeException("User is locked");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        return createTokenResponse(user);
    }


    @Override
    @Transactional
    public void requestLoginOtp(OtpRequest request) {

        UserIdentifierEntity identifier =
                userIdentifierRepository
                        .findByTypeAndValueAndEnabledTrue(
                                request.getIdentifierType(),
                                request.getIdentifier()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Identifier not found"
                                ));


        UserEntity user = identifier.getUser();

        if (!user.getEnabled()) {
            throw new RuntimeException("User is not activated");
        }

        if (user.getLocked()) {
            throw new RuntimeException("User is locked");
        }
        otpService.generateOtp(identifier, OtpPurpose.LOGIN);
    }

    @Override
    @Transactional
    public TokenResponse verifyLoginOtp(VerifyOtpRequest request) {

        UserIdentifierEntity identifier =
                userIdentifierRepository
                        .findByTypeAndValueAndEnabledTrue(
                                request.getIdentifierType(),
                                request.getIdentifier()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User identifier not found"
                                ));


        otpService.verifyOtp(
                identifier,
                OtpPurpose.LOGIN,
                request.getCode()
        );

        UserEntity user = identifier.getUser();
        return createTokenResponse(user);
    }

    @Override
    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshTokenEntity refreshTokenEntity = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        UserEntity user = refreshTokenEntity.getUser();

        /*
         * اگر کاربر غیرفعال یا قفل شده باشد
         */
        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("User is disabled");
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new RuntimeException("User is locked");
        }

        /*
         * Token Rotation
         *
         * توکن قبلی را باطل می‌کنیم
         */
        refreshTokenService.revoke(request.getRefreshToken());
        /*
         * Refresh Token جدید
         */
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        /*
         * Access Token جدید
         */
        String newAccessToken = jwtUtil.generateAccessToken(user);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtProperties.getExpirationMs())
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }

    private TokenResponse createTokenResponse(UserEntity user) {

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
//                .expiresIn(jwtProperties.getExpirationMs())
                .build();
    }

}
