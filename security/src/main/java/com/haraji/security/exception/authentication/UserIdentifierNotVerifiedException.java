package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserIdentifierNotVerifiedException extends BaseException {

    public UserIdentifierNotVerifiedException() {
        super(
                ErrorCode.USER_IDENTIFIER_NOT_VERIFIED,
                HttpStatus.FORBIDDEN,
                "user.identifier.not.verified"
        );
    }
}
