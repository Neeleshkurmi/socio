package com.genz.socio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {

    @NotBlank(message = "please enter your password")
    String prePassword;

    @NotBlank(message = "please enter your password")
    String newPassword;
}
