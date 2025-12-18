package com.elmergram.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

public class UserDto {
    public record Response(
            String status,
            List<Data> data
    ){}
    public record Create(
            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
            String username,


            @Size(max = 200, message = "Bio cannot exceed 200 characters")
            String bio,

            @Size(max = 500, message = "Profile picture URL cannot exceed 500 characters")
            String pfp_url
            ,
            @Size(min = 4,max = 12)
            @NotBlank(message = "Password is required")
            String password
    ) {}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Data(
            Integer id,
            String username,
            String pfp_url,
            Integer followers,
            Integer following,
            Instant created_at,
            String bio
    ){}
    public record Patch(
            @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
            String username,

            @Size(max = 200, message = "Bio cannot exceed 200 characters")
            String bio,

            @Size(max = 500, message = "Profile picture URL cannot exceed 500 characters")
            String pfp_url
    ) {}
}

