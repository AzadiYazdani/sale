package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {

    public InvalidPasswordException() {

        super(
                ErrorCode.INVALID_PASSWORD,
                HttpStatus.BAD_REQUEST,
                "password.invalid"
        );

    }

}
