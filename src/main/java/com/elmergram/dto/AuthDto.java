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

            @Size(max = 200, message = "Bio cannot exceed 200 characters")
            String bio,

            @Size(max = 500, message = "Profile picture URL cannot exceed 500 characters")
            String pfp_url,

            @Size(min = 4,max = 12)
            @NotBlank(message = "Password is required")
            String password
    )  {}

    public record LoginResponse(
            String jwt,
            String username
    ){}

}

