package com.haraji.security.exception.authentication;

import com.haraji.common.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("user.not.found", null, null);
    }

    public UserNotFoundException(String username) {
        super("user.not.found", null, username);
    }

}
