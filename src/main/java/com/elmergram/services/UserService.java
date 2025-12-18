package com.elmergram.services;

import com.elmergram.dto.UserDto;
import com.elmergram.exceptions.users.UserAlreadyExistsException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.User;
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

        User user = new User();
        user.setUsername(dto.username());
        user.setBio(dto.bio());
        user.setPfp_url(dto.pfp_url());
        user.setPassword(dto.password());

        userRepository.save(user);

        UserDto.Data userData = new UserDto.Data(
                user.getId(),
                user.getUsername(),
                user.getPfp_url(),
                user.getFollowers(),
                user.getFollowing(),
                user.getCreatedAt(),
                user.getBio()
        );

        return new ApiResponse.Success<>(List.of(userData));
    }


    public ApiResponse getUsers() {
        List<UserDto.Data> users = userRepository.findAll()
                .stream()
                .map(user -> new UserDto.Data(
                        user.getId(),
                        user.getUsername(),
                        user.getPfp_url(),
                        null,
                        null,
                        null,
                        user.getBio()
                ))
                .toList();
        return new ApiResponse.Success<>(users);
    }

    public ApiResponse getUser(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if(user==null){
            throw new UserNotFoundException("username not found");
        }

        UserDto.Data data = new UserDto.Data(
                user.getId(),
                user.getUsername(),
                user.getPfp_url(),
                user.getFollowers(),
                user.getFollowing(),
                user.getCreatedAt(),
                user.getBio()

        );
        return new ApiResponse.Success<>(List.of(data));
    }

    public ApiResponse updateUser(String username, UserDto.Patch dto) {
        User user = userRepository.findByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if(dto.bio()!=null && !dto.bio().isEmpty()){
            user.setBio(dto.bio());
        }
        if(dto.pfp_url()!=null && !dto.pfp_url().isEmpty()){
            user.setPfp_url(dto.pfp_url());
        }

        userRepository.save(user);

        UserDto.Data data = new UserDto.Data(
                user.getId(),
                user.getUsername(),
                user.getPfp_url(),
                user.getFollowers(),
                user.getFollowing(),
                user.getCreatedAt(),
                user.getBio()
        );
        return new ApiResponse.Success<>(data);
    }


    public void deleteUser(String username){
        User user = userRepository.findByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(user);
    }

}
