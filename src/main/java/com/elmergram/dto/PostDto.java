package com.elmergram.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

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
            MultipartFile media
    ) {}

    public record Response(
            List<Summary> content,
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean last

    ){}
    public record PostReactionsWithSummary(
            List<ReactionDto.Response> reactions,
            ReactionDto.Summary summary
    ){}
}

