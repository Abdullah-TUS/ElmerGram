package com.elmergram.controllers;

import com.elmergram.dto.PostDto;
import com.elmergram.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.POST.*;

@RestController
@RequestMapping(BASE_URL)
//@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/v1/posts
    @GetMapping()
    public ResponseEntity<PostDto.Response> getAllPosts(@PathVariable String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @GetMapping(GET_POST)
    public ResponseEntity<PostDto.Response<PostDto.Detail>> getUserPost( @PathVariable Integer postId){
        return ResponseEntity.ok(postService.getUserPost(postId));

    }

}
