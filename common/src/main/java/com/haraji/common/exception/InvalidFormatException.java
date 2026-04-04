package com.haraji.common.exception;

public class InvalidFormatException extends BaseException {

    public InvalidFormatException(String messageCode, String extraMessage){
        super(messageCode, null, extraMessage);
    }
    public InvalidFormatException(String extraMessage){
        super("violation.invalid.input", null, extraMessage);
    }

    public InvalidFormatException() {
        super("violation.invalid.input", null, null);
    }

}
