package com.genz.socio.service;

import com.genz.socio.dto.request.UpdateEmailRequest;
import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;

public interface UserService {

    AuthResponse updateUserName(String userName ,UpdateUserNameRequest update);

    UpdatePassword updatePassword(String userName, UpdatePasswordRequest update);

    UserResponse updateEmail(String userName, UpdateEmailRequest update);

}
