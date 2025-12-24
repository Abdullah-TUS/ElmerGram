package com.elmergram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    public record Login(
            String username,
            String password
    ) {}

    public record Register(
            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
            String username,

            @NotBlank(message = "Password is required")
            @Size(min = 4, max = 12)
            String password
    ) {}

    public record LoginResponse(
            String jwt,
            String username
    ){}

}

