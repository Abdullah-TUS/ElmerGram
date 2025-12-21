package com.elmergram.controllers;

import com.elmergram.dto.PostDto;
import com.elmergram.models.Post;
import com.elmergram.responses.ApiResponse;
import com.elmergram.services.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(POST_EXPLORER)
    public ResponseEntity<ApiResponse> getExplorerPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber,pageSize);
        return  ResponseEntity.ok(
                postService.getExplorerPosts(page)
        );
    }
}
