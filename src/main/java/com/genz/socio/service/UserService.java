package com.genz.socio.service;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserService {

    UserResponse updateUserName(@RequestHeader String token, @RequestBody String newUserName);

    UpdatePassword updatePassword(@RequestHeader String token, @RequestBody String prePassword, String newPassword);

    UserResponse updateEmail(@RequestHeader String token, @RequestBody String email);

    UserResponse follow(@RequestHeader String token, @RequestBody User user);
}
