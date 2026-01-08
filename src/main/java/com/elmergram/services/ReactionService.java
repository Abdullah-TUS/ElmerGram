package com.elmergram.services;

import com.elmergram.dto.PostDto;
import com.elmergram.dto.ReactionDto;
import com.elmergram.enums.ExceptionErrorMessage;
import com.elmergram.enums.ReactionType;
import com.elmergram.exceptions.posts.PostNotFoundException;
import com.elmergram.exceptions.reactions.ReactionNotFoundException;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionService {
private final ReactionRepository reactionRepository;
private final PostRepository postRepository;
private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReactionService.class);

    public ApiResponse getPostReactions(int postId) {
        List<ReactionEntity> reactions = reactionRepository.findByPost_Id(postId);

        List<ReactionDto.Response> responseList = reactions.stream()
                .map(r -> new ReactionDto.Response(
                        r.getId(),
                        r.getUser().getUsername(),
                        r.getReaction(),
                        r.getCreatedAt()
                ))
                .toList();

        // Count reactions by type
        Map<ReactionType, Long> counts = reactions.stream()
                .collect(Collectors.groupingBy(
                        ReactionEntity::getReaction,
                        Collectors.counting()
                ));

        ReactionDto.Summary summary = new ReactionDto.Summary(counts);

        return new ApiResponse.Success<>(new PostDto.PostReactionsWithSummary(responseList, summary));
    }

    @Transactional
    public void addReaction(int userId, ReactionDto.Create dto) {
        logger.debug("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn addReaction called | userId={} | postId={} | reaction={}",
                userId, dto.postId(), dto.reaction());

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
    }

    @Transactional
    public void deleteReaction(int userId, int postId){
        ReactionEntity reaction = reactionRepository.findByUserAndPost(userId,postId).orElseThrow(
                ()-> new ReactionNotFoundException(ExceptionErrorMessage.REACTION_NOT_FOUND));

        reactionRepository.delete(reaction);

    }

}
