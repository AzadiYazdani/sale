package com.haraji.common.exception;

import com.haraji.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidFormatException extends BaseException {

    public InvalidFormatException() {
        super(
                ErrorCode.INVALID_FORMAT,
                HttpStatus.BAD_REQUEST,
                "violation.invalid.input"
        );
    }

    public InvalidFormatException(Object... args) {
        super(
                ErrorCode.INVALID_FORMAT,
                HttpStatus.BAD_REQUEST,
                "violation.invalid.input",
                args
        );
    }

    public InvalidFormatException(String messageKey, Object... args) {
        super(
                ErrorCode.INVALID_FORMAT,
                HttpStatus.BAD_REQUEST,
                messageKey,
                args
        );
    }

}
