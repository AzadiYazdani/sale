package com.haraji.security.service.user;

import com.haraji.security.dto.otp.VerifyOtpRequest;
import com.haraji.security.dto.otp.VerifyOtpResponse;
import com.haraji.security.dto.register.RegisterRequest;
import com.haraji.security.dto.register.RegisterResponse;

public interface RegisterService {

    RegisterResponse register(RegisterRequest request);

    VerifyOtpResponse verifyRegisterOtp(VerifyOtpRequest request);

}
