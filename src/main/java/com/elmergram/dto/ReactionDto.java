package com.elmergram.dto;

import com.elmergram.enums.ReactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class ReactionDto {

    public record Response(
            Integer id,
            String username,
            ReactionType reaction,
            Instant createdAt
    ){}

    public record Create(
            @NotNull(message = "reaction is required")
            ReactionType reaction,

            @NotNull(message = "postId is required")
            @Positive(message = "postId must be positive")
            Integer postId

            ) {}
}
