package com.haraji.security.exception.authentication;

public class UserNotCreatedException extends RuntimeException {

    public UserNotCreatedException(String message){
        super(message);
    }


    public UserNotCreatedException() {
        super("No user created");
    }

}
