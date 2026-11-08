package com.haraji.security.exception.authentication.registration;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RegistrationIdentifierModeException extends BaseException {

    public RegistrationIdentifierModeException() {
        super(ErrorCode.REGISTRATION_IDENTIFIER_MODE_NOT_ALLOWED, HttpStatus.BAD_REQUEST,
                "registration.identifier.mode.not.allowed");
    }
}
