package com.elmergram.exceptions.reactions;

import com.elmergram.enums.ExceptionErrorMessage;

public class ReactionNotFoundException extends RuntimeException {

public ReactionNotFoundException(ExceptionErrorMessage message){
    super(message.getErrorMessage());
}
}

