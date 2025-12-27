package com.genz.socio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserNameRequest {
    @NotBlank(message = "please enter your new username")
    String userName;
}
