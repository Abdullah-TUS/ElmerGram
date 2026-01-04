package com.elmergram.exceptions.auth;

import com.elmergram.enums.ExceptionErrorMessage;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(ExceptionErrorMessage message){
        super(message.getErrorMessage());
    }
}
