package com.elmergram.services;

import com.elmergram.dto.UserDto;
import com.elmergram.exceptions.users.UserAlreadyExistsException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ApiResponse addUser(UserDto.Create dto) {

        // check if username exists
        if (userRepository.existsByUsername(dto.username())) {
            int randomSuffix = new Random().nextInt(900) + 100; // 100-999
            String suggestion = dto.username()+"_" + randomSuffix;
            throw new UserAlreadyExistsException(
                    "Username already used. Maybe try: " + suggestion
            );        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.username());
        userEntity.setBio(dto.bio());
        userEntity.setPfp_url(dto.pfp_url());
        userEntity.setPassword(dto.password());

        userRepository.save(userEntity);

        UserDto.Data userData = new UserDto.Data(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPfp_url(),
                userEntity.getFollowers(),
                userEntity.getFollowing(),
                userEntity.getCreatedAt(),
                userEntity.getBio()
        );

        return new ApiResponse.Success<>(List.of(userData));
    }


    public ApiResponse getUsers() {
        List<UserDto.Data> users = userRepository.findAll()
                .stream()
                .map(userEntity -> new UserDto.Data(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getPfp_url(),
                        null,
                        null,
                        null,
                        userEntity.getBio()
                ))
                .toList();
        return new ApiResponse.Success<>(users);
    }

    public ApiResponse getUser(String username) {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        if(userEntity ==null){
            throw new UserNotFoundException("username not found");
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
            throw new UserNotFoundException("User not found");
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
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(userEntity);
    }

}
