package com.genz.socio.mapper;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmailOrPhone(user.getEmailOrPhone());
        userResponse.setFullName(user.getFullName());
        return userResponse;
    }
}