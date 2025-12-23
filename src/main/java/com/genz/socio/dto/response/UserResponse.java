package com.genz.socio.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String userName;
    private String emailOrPhone;
    private String fullName;
}
