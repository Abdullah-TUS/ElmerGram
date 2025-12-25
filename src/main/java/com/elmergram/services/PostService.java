package com.elmergram.services;

import com.elmergram.dto.ExplorerDto;
import com.elmergram.dto.PostDto;
import com.elmergram.exceptions.posts.PostNotFoundException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.PostEntity;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.PostRepository;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public ApiResponse getUserPosts(Pageable page, String username) {

        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        if (userEntity == null) {
            throw new UserNotFoundException("username not found");
        }

        Page<PostEntity> postPage = postRepository.findByUser_Id(userEntity.getId(), page);

        Page<PostDto.Summary> dtoPage = postPage.map(post ->
                new PostDto.Summary(
                        post.getId(),
                        post.getMedia(),
                        username
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

        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(dto.username());
        if (userEntity == null) {
            throw new UserNotFoundException("username not found");
        }

        PostEntity postEntity = new PostEntity();
        postEntity.setDescription(dto.description());
        postEntity.setMedia(dto.media());
        postEntity.setUser(userEntity);

        postEntity = postRepository.save(postEntity);

        PostDto.Detail resDto = new PostDto.Detail(
                postEntity.getId(),
                postEntity.getDescription(),
                postEntity.getMedia(),
                postEntity.getLikes(),
                postEntity.getCreatedAt(),
                postEntity.getUser().getId()
        );

        return new ApiResponse.Success<>(resDto);
    }

    public ApiResponse getExplorerPosts(Pageable pageable) {
        var page = postRepository.findAllPostSummaries(pageable);
        ExplorerDto.Response response = new ExplorerDto.Response(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new ApiResponse.Success<>(response);
    }

}
