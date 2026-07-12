package com.haraji.security.service;

import com.haraji.security.api.dto.login.LoginPasswordRequest;
import com.haraji.security.api.dto.otp.OtpRequest;
import com.haraji.security.api.dto.otp.VerifyOtpRequest;
import com.haraji.security.api.dto.token.RefreshTokenRequest;
import com.haraji.security.api.dto.token.TokenResponse;

public interface LoginService {

    TokenResponse login(LoginPasswordRequest request);

    void requestLoginOtp(OtpRequest request);

    TokenResponse verifyLoginOtp(VerifyOtpRequest request );

    TokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);

}
