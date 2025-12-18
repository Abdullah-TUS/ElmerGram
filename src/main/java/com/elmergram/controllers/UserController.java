package com.elmergram.controllers;

import com.elmergram.dto.UserDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.services.PostService;
import com.elmergram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.USER.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = BASE_URL)
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService){
        this.userService=userService;
        this.postService = postService;
    }


    @PostMapping(CREATE_USER)
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserDto.Create dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
    }

    @GetMapping(GET_USERS)
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(GET_USER)
    public ResponseEntity<ApiResponse> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping(GET_USER_POSTS)
    public ResponseEntity<ApiResponse> getUserPosts(@PathVariable String username) {
        return ResponseEntity.ok(postService.getUserPosts(username));
    }

    @CrossOrigin(origins = "*")
    @PatchMapping(UPDATE_USER)
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String username,@RequestBody UserDto.Patch dto){
        return ResponseEntity.ok(userService.updateUser(username,dto));
    }

    @DeleteMapping(DELETE_USER)
    public ResponseEntity deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }



}
