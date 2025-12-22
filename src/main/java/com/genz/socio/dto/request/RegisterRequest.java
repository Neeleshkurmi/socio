package com.genz.socio.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {

    String emailOrPhone;

    String password;

    String fullName;

    String userName;
}
