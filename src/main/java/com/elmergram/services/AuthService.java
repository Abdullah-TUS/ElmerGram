package com.elmergram.services;

import com.elmergram.dto.AuthDto;
import com.elmergram.dto.UserDto;
import com.elmergram.enums.RoleName;
import com.elmergram.exceptions.auth.InvalidCredentialsException;
import com.elmergram.exceptions.users.UserAlreadyExistsException;
import com.elmergram.exceptions.users.UserNotFoundException;
import com.elmergram.models.RoleEntity;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.RoleRepository;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public void register(AuthDto.Register req){
        if(userRepository.existsByUsername(req.username())){
            throw new UserAlreadyExistsException("username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        RoleEntity defaultRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow();
        user.setRole(defaultRole);
        userRepository.save(user);
    }

    public ApiResponse login(AuthDto.Login dto) {
        var user = userRepository.findByUsernameIgnoreCase(dto.username());
        if(user==null || !encoder.matches(dto.password(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid user credentials ");
        }
        return new ApiResponse.Success<>(new UserDto.Data(user.getId(),
                user.getUsername(),user.getPfp_url(),user.getFollowers(),user.getFollowing(),user.getCreatedAt(),user.getBio()));
    }
}
