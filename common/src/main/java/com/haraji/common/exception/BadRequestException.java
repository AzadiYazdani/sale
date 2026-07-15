package com.haraji.common.exception;

import com.haraji.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException() {
        super(
                ErrorCode.BAD_REQUEST,
                HttpStatus.BAD_REQUEST,
                "bad.request"
        );
    }

    public BadRequestException(String messageKey, Object... args) {
        super(
                ErrorCode.BAD_REQUEST,
                HttpStatus.BAD_REQUEST,
                messageKey,
                args
        );
    }

}
