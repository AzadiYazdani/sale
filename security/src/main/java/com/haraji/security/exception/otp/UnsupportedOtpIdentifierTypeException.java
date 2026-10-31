package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnsupportedOtpIdentifierTypeException extends BaseException {

    public UnsupportedOtpIdentifierTypeException() {
        super(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST,
                "otp.identifier.type.not.supported");
    }
}
