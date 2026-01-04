package com.elmergram.exceptions.posts;

import com.elmergram.enums.ExceptionErrorMessage;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(ExceptionErrorMessage message){
        super(message.getErrorMessage());
    }
}
