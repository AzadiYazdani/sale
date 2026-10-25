package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserLockedException extends BaseException {

    public UserLockedException() {
        super(
                ErrorCode.USER_LOCKED,
                HttpStatus.FORBIDDEN,
                "user.locked"
        );
    }
}
