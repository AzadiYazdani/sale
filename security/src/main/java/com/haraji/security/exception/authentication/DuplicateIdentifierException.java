package com.haraji.security.exception.authentication;

import com.haraji.common.exception.BaseException;

public class DuplicateIdentifierException extends BaseException {

    public DuplicateIdentifierException(String messageKey, String code, String extraData) {
        super(messageKey, code, extraData);
    }

    public DuplicateIdentifierException(String messageCode, String extraMessage){
        super(messageCode, null, extraMessage);
    }

    public DuplicateIdentifierException() {
        super("duplicate.identifier", null, null);
    }

}
