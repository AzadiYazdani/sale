package com.haraji.security.exception.authentication.registration;


import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidRegistrationRequestException extends BaseException {

    public InvalidRegistrationRequestException() {
        super(ErrorCode.INVALID_REGISTRATION_REQUEST, HttpStatus.BAD_REQUEST,
                "registration.invalid.request");
    }
}
