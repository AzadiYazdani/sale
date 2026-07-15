package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidOtpException extends BaseException {

    public InvalidOtpException() {

        super(
                ErrorCode.INVALID_OTP,
                HttpStatus.BAD_REQUEST,
                "otp.invalid"
        );

    }

}
