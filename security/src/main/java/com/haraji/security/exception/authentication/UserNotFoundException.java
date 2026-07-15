package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {

        super(
                ErrorCode.USER_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "user.not.found"
        );

    }

    public UserNotFoundException(String identifier) {
        super(
                ErrorCode.USER_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "user.not.found",
                identifier
        );
    }

}
