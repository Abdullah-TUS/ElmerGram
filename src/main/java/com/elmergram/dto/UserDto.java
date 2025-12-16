package com.elmergram.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

public class UserDto {
    public record Response(
            String status,
            List<Data> data
    ){}
    public record Create(){}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Data(
            Integer id,
            String username,
            String pfp_url,
            Integer followers,
            Integer following,
            Date created_at
    ){}
}

