package com.elmergram.services;

import com.elmergram.dto.UserDto;
import com.elmergram.models.User;
import com.elmergram.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public UserDto.Response getUsers() {
        List<UserDto.Data> users = userRepository.findAll()
                .stream()
                .map(user -> new UserDto.Data(
                        user.getId(),
                        user.getUsername(),
                        user.getPfp_url(),
                        null,
                        null,
                        null
                ))
                .toList();

        return new UserDto.Response("success", users);
    }

    public UserDto.Response getUser(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);

        UserDto.Data data = new UserDto.Data(
                user.getId(),
                user.getUsername(),
                user.getPfp_url(),
                user.getFollowers(),
                user.getFollowing(),
                user.getCreatedAt()
        );
        return new UserDto.Response("Success", List.of(data));
    }

}
