package com.haraji.security.service.user;

import com.haraji.security.config.OtpChannelProperties;
import com.haraji.security.constant.IdentifierType;
import com.haraji.security.dto.login.LoginPasswordRequest;
import com.haraji.security.dto.otp.OtpRequest;
import com.haraji.security.dto.otp.VerifyOtpRequest;
import com.haraji.security.dto.token.RefreshTokenRequest;
import com.haraji.security.dto.token.TokenResponse;
import com.haraji.security.config.JwtProperties;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.UserIdentifierRepository;
import com.haraji.security.exception.authentication.InvalidPasswordException;
import com.haraji.security.service.otp.OtpService;
import com.haraji.security.service.token.RefreshTokenService;
import com.haraji.security.util.CommonUtil;
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
    private final OtpChannelProperties otpChannelProperties;


    @Override
    public TokenResponse loginPassword(LoginPasswordRequest request) {

        UserIdentifierEntity identifier = getIdentifier(request.getIdentifierType(), request.getIdentifier());
        if (!passwordEncoder.matches(request.getPassword(), identifier.getUser().getPasswordHash())) {
            throw new InvalidPasswordException();
        }
        return createTokenResponse(identifier.getUser());
    }


    @Override
    public void loginOtp(OtpRequest request) {
        UserIdentifierEntity identifier = getIdentifier(
                request.getIdentifierType(),
                request.getIdentifier()
        );
        validateOtpChannelEnabled(identifier.getType());
        otpService.generateOtp(identifier, OtpPurpose.LOGIN);
    }

    @Override
    public TokenResponse verifyLoginOtp(VerifyOtpRequest request) {
        UserIdentifierEntity identifier = findIdentifier(request.getIdentifierType(), request.getIdentifier());
        UserEntity user = identifier.getUser();

        validateUserForLogin(identifier, user);
        validateOtpChannelEnabled(identifier.getType());

        otpService.verifyOtp(identifier, OtpPurpose.LOGIN, request.getCode());
        return createTokenResponse(user);
    }


    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshTokenEntity refreshTokenEntity = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        UserEntity user = refreshTokenEntity.getUser();
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
                .expiresIn(jwtProperties.getExpirationMs() / 1000)
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }


    /// /////////////////////private

    private UserIdentifierEntity getIdentifier(IdentifierType type, String rawValue) {
        UserIdentifierEntity identifier = findIdentifier(type, rawValue);
        validateUserForLogin(identifier, identifier.getUser());
        return identifier;
    }

    private TokenResponse createTokenResponse(UserEntity user) {
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtProperties.getExpirationMs()/1000)
                .build();
    }

    private UserIdentifierEntity findIdentifier(IdentifierType type, String rawValue) {

        String value = CommonUtil.normalizeIdentifier(rawValue, type);
        return userIdentifierRepository
                .findByTypeAndValueAndEnabledTrue(type, value)
                .orElseThrow(() ->
                        new IllegalArgumentException("شناسه یا رمز عبور نادرست است.")
                );
    }

    private void validateUserForLogin(UserIdentifierEntity identifier, UserEntity user) {

        if (!Boolean.TRUE.equals(identifier.getVerified())) {
            throw new IllegalStateException("ابتدا باید شناسهٔ خود را تأیید کنید.");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new IllegalStateException("حساب کاربری فعال نیست.");
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new IllegalStateException("حساب کاربری قفل شده است.");
        }
    }

    private void validateOtpChannelEnabled(IdentifierType type) {
        if (type == IdentifierType.EMAIL && !otpChannelProperties.getEmail().isEnabled()) {
            throw new IllegalStateException("ارسال کد از طریق ایمیل غیرفعال است.");
        }

        if (type == IdentifierType.PHONE && !otpChannelProperties.getPhone().isEnabled()) {
            throw new IllegalStateException("ارسال کد از طریق پیامک غیرفعال است.");
        }
    }
}
