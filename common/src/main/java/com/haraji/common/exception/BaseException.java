package com.haraji.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String messageKey;
    private final String code;
    private final String extraData;

    public BaseException(String messageKey, String code, String extraData) {
        this.messageKey = messageKey;
        this.code = code;
        this.extraData = extraData;
    }
}

