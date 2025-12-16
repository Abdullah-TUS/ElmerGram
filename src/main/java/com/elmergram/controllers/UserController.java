package com.elmergram.controllers;

import com.elmergram.constants.URLs;
import com.elmergram.dto.PostDto;
import com.elmergram.dto.UserDto;
import com.elmergram.models.User;
import com.elmergram.services.PostService;
import com.elmergram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.USER.*;


@RestController
@RequestMapping(path = BASE_URL)
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService){
        this.userService=userService;
        this.postService = postService;
    }

    @GetMapping(GET_USERS)
    public ResponseEntity<UserDto.Response> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(GET_USER)
    public ResponseEntity<UserDto.Response> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping(GET_USER_POSTS)
    public ResponseEntity<PostDto.Response<PostDto.Summary>> getUserPosts(@PathVariable String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }


}
