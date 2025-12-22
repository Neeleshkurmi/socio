package com.genz.socio.service;

import com.genz.socio.dto.request.LoginRequest;
import com.genz.socio.dto.request.RegisterRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.AuthResponse;

public interface UserService {

    ApiResponse<AuthResponse> saveUser(RegisterRequest request);

    ApiResponse<AuthResponse> getUser(LoginRequest request);
}