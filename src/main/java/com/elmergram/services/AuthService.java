package com.elmergram.services;

import com.elmergram.dto.AuthDto;
import com.elmergram.enums.RoleName;
import com.elmergram.exceptions.auth.InvalidCredentialsException;
import com.elmergram.exceptions.users.UserAlreadyExistsException;
import com.elmergram.security.jwt.JwtUtils;
import com.elmergram.models.RoleEntity;
import com.elmergram.models.UserEntity;
import com.elmergram.repositories.RoleRepository;
import com.elmergram.repositories.UserRepository;
import com.elmergram.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public void register(AuthDto.Register req){
        if(userRepository.existsByUsername(req.username())){
            throw new UserAlreadyExistsException("username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        if(req.bio()!=null) user.setBio(req.bio());
        if(req.pfp_url()!=null) user.setPfp_url(req.pfp_url());


        RoleEntity defaultRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow();
        user.setRole(defaultRole);
        userRepository.save(user);
    }

    public ApiResponse login(AuthDto.Login dto) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.username(),
                            dto.password()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid user credentials");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);


        return new ApiResponse.Success<>(new AuthDto.LoginResponse(jwtToken,userDetails.getUsername()));
    }

}
