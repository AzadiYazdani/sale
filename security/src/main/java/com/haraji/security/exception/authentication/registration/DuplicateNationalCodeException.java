package com.haraji.security.exception.authentication.registration;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateNationalCodeException extends BaseException {

    public DuplicateNationalCodeException() {
        super(
                ErrorCode.DUPLICATE_NATIONAL_CODE,
                HttpStatus.CONFLICT,
                "duplicate.national.code"
        );
    }
}
