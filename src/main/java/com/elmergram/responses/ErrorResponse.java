package com.elmergram.responses;


import lombok.Data;

@Data
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private String path;
}
