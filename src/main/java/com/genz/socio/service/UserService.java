package com.genz.socio.service;

import com.genz.socio.dto.request.UpdateEmailRequest;
import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;

public interface UserService {

    AuthResponse updateUserName(String token, UpdateUserNameRequest update);

    UpdatePassword updatePassword(String token,  UpdatePasswordRequest update);

    UserResponse updateEmail(String token, UpdateEmailRequest update);

    ProfileResponse follow(String token, Long id);
}
