package com.haraji.baseinfo.exception;

import com.haraji.common.exception.BaseException;

public class BusinessTypeNotFoundException extends BaseException {
    public BusinessTypeNotFoundException() {
        super("business.type.not.found", null, null);
    }

    public BusinessTypeNotFoundException(Long id) {
        super("business.type.not.found", null, String.valueOf(id));
    }

}
