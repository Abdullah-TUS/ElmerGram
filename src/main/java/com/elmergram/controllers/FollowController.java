package com.elmergram.controllers;


import com.elmergram.constants.URLs;
import com.elmergram.security.SecurityUtils;
import com.elmergram.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.FOLLOW.*;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final SecurityUtils securityUtils;

    @GetMapping(FOLLOWERS)
    public ResponseEntity<?> getFollowersCount(@PathVariable String username){
        return ResponseEntity.ok().body(followService.followersCount(username));
    };

    @GetMapping(FOLLOWING)
    public ResponseEntity<?> getFollowingCount(@PathVariable String username){
        return ResponseEntity.ok().body(followService.followingCount(username));
    };

    @PostMapping(FOLLOW_USER)
    public ResponseEntity<?> follow(@PathVariable String username){
        int userId = securityUtils.getCurrentUserId();
        followService.followUser(userId,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(UNFOLLOW_USER)
    public ResponseEntity<?> unfollow(@PathVariable String username){
        int userId = securityUtils.getCurrentUserId();
        followService.removeFollow(userId,username);
        return ResponseEntity.noContent().build();
    }
}

