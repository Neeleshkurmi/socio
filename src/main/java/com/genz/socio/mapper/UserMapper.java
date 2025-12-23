package com.genz.socio.mapper;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.UserResponse;

public class UserMapper {

    public static UserResponse toResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setEmailOrPhone(user.getEmailOrPhone());
        userResponse.setFullName(user.getFullName());
        return userResponse;
    }
}