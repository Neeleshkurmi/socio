package com.genz.socio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "please enter your email or phone")
    String emailOrPhone;

    @NotBlank(message = "please enter your password")
    String password;

    @NotBlank(message = "please enter your full name")
    String fullName;

    @NotBlank(message = "please enter your unique username")
    String userName;
}
