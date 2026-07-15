package com.haraji.common.exception;

import com.haraji.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    private final HttpStatus status;

    private final String messageKey;

    private final Object[] args;

    public BaseException(
            ErrorCode errorCode,
            HttpStatus status,
            String messageKey,
            Object... args) {

        this.errorCode = errorCode;
        this.status = status;
        this.messageKey = messageKey;
        this.args = args;
    }
}
