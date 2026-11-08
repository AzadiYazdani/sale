package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OtpExpiredException extends BaseException {

    public OtpExpiredException() {
        super(ErrorCode.OTP_EXPIRED, HttpStatus.BAD_REQUEST, "otp.expired");
    }
}
