package com.elmergram.enums;

import lombok.Getter;

@Getter
public enum ExceptionErrorMessage {

    // USER
    USER_NOT_FOUND("user not found"),
    USER_ALREADY_EXISTS("username already exists"),

    // AUTH
    INVALID_CREDENTIALS("invalid user credentials"),
    UNAUTHORIZED("unauthorized access"),

    // POST
    POST_NOT_FOUND("post not found"),

    // MEDIA
    MEDIA_REQUIRED("media file is required"),
    MEDIA_UPLOAD_FAILED("failed to upload media file"),

    // REACTION
    REACTION_NOT_FOUND("reaction not found");

    private final String errorMessage;

    ExceptionErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

