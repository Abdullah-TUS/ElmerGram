package com.elmergram.services;

import com.elmergram.dto.PostDto;
import com.elmergram.repositories.PostRepository;
import com.elmergram.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostDto.Response getUserPosts(String username) {

        Integer userId = userRepository.findByUsernameIgnoreCase(username).getId();
        if(userId==null)
            throw new IllegalArgumentException("wrong username");

        List<PostDto.Summary> posts = postRepository.findByUserId(userId).stream().map(
                post -> new PostDto.Summary(
                        post.getId(),
                        post.getMedia()
                )
        ).toList();

        return new PostDto.Response<>("success", posts);
    }

    public PostDto.Response<PostDto.Detail> getUserPost( Integer postId){

        PostDto.Detail dto = postRepository.findById(postId).map(
                post -> new PostDto.Detail(
                        post.getId(),
                        post.getDescription(),
                        post.getMedia(),
                        post.getLikes(),
                        post.getCreatedAt(),
                        post.getUser().getId()
                )
                        ).orElseThrow(()-> new DuplicateFormatFlagsException("blabla"));
        return new PostDto.Response<>("success",List.of(dto));
    }
}
