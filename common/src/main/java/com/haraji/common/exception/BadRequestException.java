package com.haraji.common.exception;

public class BadRequestException extends BaseException {

    public BadRequestException() {
        super("the received request was not valid ", null, null);
    }

}
