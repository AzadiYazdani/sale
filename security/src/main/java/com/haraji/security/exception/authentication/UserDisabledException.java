package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserDisabledException extends BaseException {

    public UserDisabledException() {
        super(
                ErrorCode.USER_DISABLED,
                HttpStatus.FORBIDDEN,
                "user.disabled"
        );
    }
}
