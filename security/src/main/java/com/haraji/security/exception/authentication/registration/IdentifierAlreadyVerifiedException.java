package com.haraji.security.exception.authentication.registration;


import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class IdentifierAlreadyVerifiedException extends BaseException {

    public IdentifierAlreadyVerifiedException() {
        super(ErrorCode.IDENTIFIER_ALREADY_VERIFIED, HttpStatus.CONFLICT,
                "identifier.already.verified");
    }
}
