package com.elmergram.responses;

import java.time.Instant;
import java.util.List;

public class ApiResponse {
    public static class Success<T> extends ApiResponse {
        public final T data;
        public final Instant timestamp;

        public Success(T data) {
            this.data = data;
            this.timestamp = Instant.now();
        }
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


