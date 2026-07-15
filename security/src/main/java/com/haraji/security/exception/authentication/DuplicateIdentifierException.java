package com.haraji.security.exception.authentication;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import com.haraji.security.constant.IdentifierType;
import org.springframework.http.HttpStatus;

public class DuplicateIdentifierException extends BaseException {

    public DuplicateIdentifierException(IdentifierType identifierType) {

        super(
                ErrorCode.DUPLICATE_IDENTIFIER,
                HttpStatus.CONFLICT,
                "duplicate.identifier",
                identifierType
        );
    }

}
