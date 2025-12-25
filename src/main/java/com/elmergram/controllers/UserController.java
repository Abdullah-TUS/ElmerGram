package com.elmergram.controllers;

import com.elmergram.dto.UserDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.services.PostService;
import com.elmergram.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.USER.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = BASE_URL)
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_USERS)
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(GET_USER)
    public ResponseEntity<ApiResponse> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping(GET_USER_POSTS)
    public ResponseEntity<ApiResponse> getUserPosts(@PathVariable String username,
                                                    @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize
                                                    ) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        return ResponseEntity.ok(postService.getUserPosts(page,username));
    }

    @PatchMapping(UPDATE_USER)
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String username,@RequestBody UserDto.Patch dto){
        return ResponseEntity.ok(userService.updateUser(username,dto));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
