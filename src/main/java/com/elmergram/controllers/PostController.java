package com.elmergram.controllers;

import com.elmergram.dto.PostDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.POST.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(BASE_URL)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(GET_POST)
    public ResponseEntity<ApiResponse> getUserPost( @PathVariable Integer postId){
        return ResponseEntity.ok(postService.getPostDetails(postId));

    }

    @PostMapping(POST_POST)
    public ResponseEntity<ApiResponse> addPost(@RequestBody @Valid PostDto.Create dto){
        return ResponseEntity.ok(postService.addPost(dto));
    }
}
