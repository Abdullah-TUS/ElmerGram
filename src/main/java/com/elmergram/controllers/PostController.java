package com.elmergram.controllers;

import com.elmergram.dto.PostDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.security.SecurityUtils;
import com.elmergram.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.POST.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class PostController {

    private final PostService postService;
    private final SecurityUtils securityUtils;


    @GetMapping(GET_POST)
    public ResponseEntity<ApiResponse> getUserPost( @PathVariable Integer postId){
        return ResponseEntity.ok(postService.getPostDetails(postId));

    }

    @PostMapping(value = POST_POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addPost( @ModelAttribute @Valid PostDto.Create dto){
        Integer userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(postService.addPost(dto,userId));
    }

    @GetMapping(POST_EXPLORER)
    public ResponseEntity<ApiResponse> getExplorerPosts(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber-1,pageSize);
        return  ResponseEntity.ok(
                postService.getExplorerPosts(page)
        );
    }
}
