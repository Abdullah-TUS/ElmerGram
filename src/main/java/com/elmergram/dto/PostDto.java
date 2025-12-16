package com.elmergram.dto;

import java.time.Instant;
import java.util.List;

public class PostDto {

    public record Response<T>(
            String status,
            List<T> data
    ) {}

    public record Summary(
            Integer id,
            String media
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
            String mediaUrl,
            Integer userId
    ) {}
}

