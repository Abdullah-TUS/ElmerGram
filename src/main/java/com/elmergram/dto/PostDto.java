package com.elmergram.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public class PostDto {


    public record Summary(
            @NotBlank
            @Min(1)
            Integer id,
            String media,
            String username
    ) {}

    public record Detail(
            Integer id,
            String description,
            String mediaUrl,
            Integer likes,
            Instant createdAt,
            Integer userId
    ) {}

    public record Create(
            String description,
            @NotBlank(message = "a post must have media.")
            String media,
            @NotNull(message = "user id must be provided.")
            @Min(value = 1, message = "user id must be at least 1.")
            String username
    ) {}

    public record Response(
            List<Summary> content,
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean last

    ){}

}

