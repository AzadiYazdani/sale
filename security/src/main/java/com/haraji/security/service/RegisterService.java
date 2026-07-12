package com.haraji.security.service;

import com.haraji.security.api.dto.otp.VerifyOtpRequest;
import com.haraji.security.api.dto.otp.VerifyOtpResponse;
import com.haraji.security.api.dto.register.RegisterRequest;
import com.haraji.security.api.dto.register.RegisterResponse;

public interface RegisterService {

    RegisterResponse register(RegisterRequest request);

    VerifyOtpResponse verifyRegisterOtp(VerifyOtpRequest request);

}
