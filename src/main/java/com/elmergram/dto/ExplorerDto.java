package com.elmergram.dto;

import java.util.List;

public class ExplorerDto {

    public record Response(
            List<PostDto.Summary> content,
            int pageNumber,
            int pageSize,
            int totalElements,
            int totalPages,
            boolean last

    ){}
}
