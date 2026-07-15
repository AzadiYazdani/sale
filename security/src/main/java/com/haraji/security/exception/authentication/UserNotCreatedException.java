package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotCreatedException extends BaseException {

    public UserNotCreatedException() {
        super(
                ErrorCode.USER_NOT_CREATED,
                HttpStatus.BAD_REQUEST,
                "user.not.created"
        );
    }

    public UserNotCreatedException(Object... args) {
        super(
                ErrorCode.USER_NOT_CREATED,
                HttpStatus.BAD_REQUEST,
                "user.not.created",
                args
        );
    }

}
