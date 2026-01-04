package com.elmergram.exceptions.users;

import com.elmergram.enums.ExceptionErrorMessage;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(ExceptionErrorMessage message){
        super(message.getErrorMessage());
    }
}
