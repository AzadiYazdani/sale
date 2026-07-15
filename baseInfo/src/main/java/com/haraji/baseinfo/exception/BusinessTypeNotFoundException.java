package com.haraji.baseinfo.exception;

import com.haraji.common.exception.BaseException;
import com.haraji.common.constant.ErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessTypeNotFoundException extends BaseException {

    public BusinessTypeNotFoundException() {
        super(ErrorCode.BUSINESS_TYPE_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "business.type.not.found"
        );
    }

    public BusinessTypeNotFoundException(Integer id) {
        super(
                ErrorCode.BUSINESS_TYPE_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                "business.type.not.found",
                id
        );
    }

}
