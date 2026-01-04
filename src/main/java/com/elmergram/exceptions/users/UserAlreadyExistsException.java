package com.elmergram.exceptions.users;

import com.elmergram.enums.ExceptionErrorMessage;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(ExceptionErrorMessage message){
        super(message.getErrorMessage());
    }
}
