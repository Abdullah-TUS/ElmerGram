package com.elmergram.advices;

import com.elmergram.exceptions.auth.InvalidCredentialsException;
import com.elmergram.exceptions.posts.PostNotFoundException;
import com.elmergram.exceptions.users.UserAlreadyExistsException;
import com.elmergram.exceptions.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.elmergram.responses.ErrorResponse;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String message,
            WebRequest req
    ) {
        ErrorResponse res = new ErrorResponse();
        res.setMessage(message);
        res.setStatusCode(status.value());
        res.setPath(req.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(res, status);
    }

    // --- Custom exceptions ---
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest req){
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExistsException(UserAlreadyExistsException ex, WebRequest req){
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex, WebRequest req){
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest req){
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), req); // changed from BAD_REQUEST to UNAUTHORIZED
    }

    // --- Spring Security exceptions ---
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex, WebRequest req) {
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest req){
        return buildError(HttpStatus.FORBIDDEN, "You do not have permission to access this resource", req);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientAuthException(InsufficientAuthenticationException ex, WebRequest req){
        return buildError(HttpStatus.UNAUTHORIZED, "Authentication is required to access this resource", req);
    }

    // --- General fallback ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest req){
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went VERY wrong: "+ex.getMessage(), req);
    }
}


