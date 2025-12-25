package com.genz.socio.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    String prePassword;
    String newPassword;
}
