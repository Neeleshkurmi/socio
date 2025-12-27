package com.genz.socio.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmailRequest {
    @Email(message = "please enter valid email format")
    @NotBlank(message = "please enter your email address")
    String email;
}