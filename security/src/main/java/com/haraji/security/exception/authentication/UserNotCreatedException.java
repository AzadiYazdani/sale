package com.haraji.security.exception.authentication;

import com.haraji.common.exception.BaseException;

public class UserNotCreatedException extends BaseException {

    public UserNotCreatedException(String messageCode, String extraMessage){
        super(messageCode, null, extraMessage);
    }

    public UserNotCreatedException() {
        super("user.not.created", null, null);
    }

}
