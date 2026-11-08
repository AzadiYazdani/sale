package com.haraji.security.service.user;

import com.haraji.security.dto.login.LoginPasswordRequest;
import com.haraji.security.dto.otp.OtpRequest;
import com.haraji.security.dto.otp.VerifyOtpRequest;
import com.haraji.security.dto.token.RefreshTokenRequest;
import com.haraji.security.dto.token.TokenResponse;

public interface LoginService {

    TokenResponse loginPassword(LoginPasswordRequest request);

    void loginOtp(OtpRequest request);

    TokenResponse verifyLoginOtp(VerifyOtpRequest request );

    TokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);

}
