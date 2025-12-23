package com.genz.socio.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserResponse userResponse;
}