package com.elmergram.responses;

import java.time.Instant;
import java.util.List;

public class ApiResponse {
    public static class Success<T> extends ApiResponse {
        public T data;
        public Instant timestamp = Instant.now();

        public Success(T data) { this.data = data; }
    }

    public static class Error extends ApiResponse {
        public Integer status;
        public String message;
        public String path;
        public Instant timestamp = Instant.now();
        public List<String> errors;

        public Error(Integer status, String message, String path, List<String> errors) {
            this.status = status;
            this.message = message;
            this.path = path;
            this.errors = errors;
        }
    }
}


