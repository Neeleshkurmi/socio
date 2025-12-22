package com.genz.socio.controller;

import com.genz.socio.dto.request.LoginRequest;
import com.genz.socio.dto.request.RegisterRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return userService.getUser(loginRequest);
    }

    @PostMapping("register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return userService.saveUser(registerRequest);
    }
}
