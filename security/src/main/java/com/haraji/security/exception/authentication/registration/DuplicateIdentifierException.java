package com.haraji.security.exception.authentication.registration;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateIdentifierException extends BaseException {

    public DuplicateIdentifierException(Object... args) {
        super(ErrorCode.DUPLICATE_IDENTIFIER, HttpStatus.CONFLICT,
                "identifier.duplicate", args);
    }
}
