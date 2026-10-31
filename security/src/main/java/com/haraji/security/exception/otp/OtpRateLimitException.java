package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OtpRateLimitException extends BaseException {

    public OtpRateLimitException(String messageKey) {
        super(ErrorCode.OTP_RATE_LIMITED, HttpStatus.TOO_MANY_REQUESTS, messageKey);
    }
}
