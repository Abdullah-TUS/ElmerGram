package com.elmergram.controllers;

import com.elmergram.dto.ReactionDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.security.SecurityUtils;
import com.elmergram.services.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.elmergram.constants.URLs.REACTION.*;


    @RestController
    @RequestMapping(BASE_URL)
    @RequiredArgsConstructor
    public class ReactionController {
    private final ReactionService reactionService;
    private final SecurityUtils securityUtils;

        @GetMapping(GET_POST_REACTIONS)
        public ResponseEntity<ApiResponse> getPostReactions(@PathVariable int postId){
            return ResponseEntity.ok().body( reactionService.getPostReactions(postId));
        }

    @PostMapping(BASE_URL)
    public ResponseEntity<ApiResponse> createPostReaction(@Valid @RequestBody ReactionDto.Create dto){
         int userId = securityUtils.getCurrentUserId();
         return ResponseEntity.ok().body(reactionService.addReaction(userId,dto));
    }

}
