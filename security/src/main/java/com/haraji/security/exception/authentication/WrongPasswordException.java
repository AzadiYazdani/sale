package com.haraji.security.exception.authentication;

import com.haraji.common.exception.BaseException;

public class WrongPasswordException extends BaseException {

    public WrongPasswordException() {
        super("password.wrong", null, null);
    }

    public WrongPasswordException(String username) {
        super("password.wrong", null, username);
    }

}
