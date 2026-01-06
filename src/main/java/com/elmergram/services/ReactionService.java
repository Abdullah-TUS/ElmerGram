package com.elmergram.services;

import com.elmergram.dto.ReactionDto;
import com.elmergram.enums.ExceptionErrorMessage;
import com.elmergram.exceptions.posts.PostNotFoundException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.PostEntity;
import com.elmergram.models.ReactionEntity;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.PostRepository;
import com.elmergram.repositories.ReactionRepository;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService {
private final ReactionRepository reactionRepository;
private final PostRepository postRepository;
private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReactionService.class);

    public ApiResponse getPostReactions(int postId) {
        List<ReactionDto.Response> responses =
                reactionRepository.findByPost_Id(postId)
                        .stream()
                        .map(r -> new ReactionDto.Response(
                                r.getId(),
                                r.getUser().getUsername(),
                                r.getReaction(),
                                r.getCreatedAt()
                        ))
                        .toList();

        return new ApiResponse.Success<>(responses);
    }

    @Transactional
    public ApiResponse addReaction(int userId, ReactionDto.Create dto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        ExceptionErrorMessage.USER_NOT_FOUND));

        PostEntity post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new PostNotFoundException(
                        ExceptionErrorMessage.POST_NOT_FOUND));

        Optional<ReactionEntity> existing =
                reactionRepository.findByUserAndPost(user, post);

        ReactionEntity reaction;

        if (existing.isPresent()) {
            reaction = existing.get();
            reaction.setReaction(dto.reaction());
        } else {
            reaction = new ReactionEntity();
            reaction.setUser(user);
            reaction.setPost(post);
            reaction.setReaction(dto.reaction());
        }

        reactionRepository.save(reaction);
        return new ApiResponse.Success<>("Reaction saved");
    }

}
