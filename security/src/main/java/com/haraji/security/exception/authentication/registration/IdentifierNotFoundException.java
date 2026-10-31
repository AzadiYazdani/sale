package com.haraji.security.exception.authentication.registration;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class IdentifierNotFoundException extends BaseException {

    public IdentifierNotFoundException() {
        super(ErrorCode.IDENTIFIER_NOT_FOUND, HttpStatus.NOT_FOUND,
                "identifier.not.found");
    }
}
