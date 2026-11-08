package com.haraji.security.exception.authentication.token;


import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RefreshTokenReuseException extends BaseException {

    public RefreshTokenReuseException() {
        super(ErrorCode.REFRESH_TOKEN_REUSE_DETECTED, HttpStatus.UNAUTHORIZED, "refresh.token.reuse.detected");
    }
}

