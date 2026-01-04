package com.elmergram.services;

import com.elmergram.dto.UserDto;
import com.elmergram.enums.ExceptionErrorMessage;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ApiResponse getUsers(Pageable pageable) {

        Page<UserEntity> page = userRepository.findAll(pageable);

        List<UserDto.Data> users = page.getContent()
                .stream()
                .map(user -> new UserDto.Data(
                        user.getId(),
                        user.getUsername(),
                        user.getPfp_url(),
                        user.getFollowers(),
                        user.getFollowing(),
                        user.getCreatedAt(),
                        user.getBio()
                ))
                .toList();

        UserDto.Response res = new UserDto.Response(
                users,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );

        return new ApiResponse.Success<>(res);
    }


    public ApiResponse getUser(String username) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        if(userEntity ==null){
            throw new UserNotFoundException(ExceptionErrorMessage.USER_NOT_FOUND);
        }

        UserDto.Data data = new UserDto.Data(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPfp_url(),
                userEntity.getFollowers(),
                userEntity.getFollowing(),
                userEntity.getCreatedAt(),
                userEntity.getBio()

        );
        return new ApiResponse.Success<>(data);
    }

    public ApiResponse updateUser(String username, UserDto.Patch dto) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);

        if (userEntity == null) {
            throw new UserNotFoundException(ExceptionErrorMessage.USER_NOT_FOUND);
        }

        if(dto.bio()!=null && !dto.bio().isEmpty()){
            userEntity.setBio(dto.bio());
        }
        if(dto.pfp_url()!=null && !dto.pfp_url().isEmpty()){
            userEntity.setPfp_url(dto.pfp_url());
        }

        userRepository.save(userEntity);

        UserDto.Data data = new UserDto.Data(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPfp_url(),
                userEntity.getFollowers(),
                userEntity.getFollowing(),
                userEntity.getCreatedAt(),
                userEntity.getBio()
        );
        return new ApiResponse.Success<>(data);
    }


    public void deleteUser(String username){
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);

        if (userEntity == null) {
            throw new UserNotFoundException(ExceptionErrorMessage.USER_NOT_FOUND);
        }
        userRepository.delete(userEntity);
    }

}
