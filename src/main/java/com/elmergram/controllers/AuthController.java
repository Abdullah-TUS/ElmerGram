package com.elmergram.controllers;

import com.elmergram.dto.AuthDto;
import com.elmergram.responses.ApiResponse;
import com.elmergram.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.elmergram.constants.URLs.AUTH.*;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping(REGISTER)
    public ResponseEntity<ApiResponse.Success<String>> register(@Valid @RequestBody AuthDto.Register dto){
        authService.register(dto);
        return ResponseEntity.ok(new ApiResponse.Success<>("User registered"));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<ApiResponse> login (@Valid @RequestBody AuthDto.Login dto){
        return ResponseEntity.ok().body(authService.login(dto));

    }

}
