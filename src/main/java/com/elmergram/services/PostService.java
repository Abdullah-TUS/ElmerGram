package com.elmergram.services;

import com.elmergram.dto.ExplorerDto;
import com.elmergram.dto.PostDto;
import com.elmergram.exceptions.posts.PostNotFoundException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.Post;
import com.elmergram.models.User;
import com.elmergram.repositories.PostRepository;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse getUserPosts(Pageable page, String username) {

        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UserNotFoundException("username not found");
        }

        Page<Post> postPage = postRepository.findByUserId(user.getId(), page);

        Page<PostDto.Summary> dtoPage = postPage.map(post ->
                new PostDto.Summary(
                        post.getId(),
                        post.getMedia()
                )
        );

        PostDto.Response response = new PostDto.Response(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.isLast()
        );

        return new ApiResponse.Success<>(response);
    }



    public ApiResponse getPostDetails( Integer postId){

        PostDto.Detail dto = postRepository.findById(postId).map(
                post -> new PostDto.Detail(
                        post.getId(),
                        post.getDescription(),
                        post.getMedia(),
                        post.getLikes(),
                        post.getCreatedAt(),
                        post.getUser().getId()
                )
                        ).orElseThrow(()-> new PostNotFoundException("couldn't find post with the id: "+postId+". wrong id perhaps?"));
        return new ApiResponse.Success<>(dto);
    }

    @Transactional
    public ApiResponse addPost(PostDto.Create dto){

        User user = userRepository.findByUsernameIgnoreCase(dto.username());
        if (user == null) {
            throw new UserNotFoundException("username not found");
        }

        Post post = new Post();
        post.setDescription(dto.description());
        post.setMedia(dto.media());
        post.setUser(user);

        post = postRepository.save(post);

        PostDto.Detail resDto = new PostDto.Detail(
                post.getId(),
                post.getDescription(),
                post.getMedia(),
                post.getLikes(),
                post.getCreatedAt(),
                post.getUser().getId()
        );

        return new ApiResponse.Success<>(resDto);
    }

    public ApiResponse getExplorerPosts(Pageable pageable) {
        var page = postRepository.findAll(pageable);

        ExplorerDto.Response response = new ExplorerDto.Response(
                page.getContent().stream().map(
                        post -> new PostDto.Summary(
                                post.getId(),
                                post.getMedia()
                        )
                ).toList(),
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new ApiResponse.Success<>(response);
    }

}
