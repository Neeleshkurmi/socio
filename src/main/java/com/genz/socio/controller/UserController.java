package com.genz.socio.controller;

import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.service.UserService;
import com.genz.socio.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private Logger logger = Logger.getLogger("logger");
    private final UserServiceImpl userService;

    @PostMapping("/change-password")
    public ApiResponse<UpdatePassword> changePassword(@RequestHeader("Authorization") String token,
                                                      @RequestBody UpdatePasswordRequest update){
        logger.info("hitting the request change password");
        return new ApiResponse<>(true,"password changed"
                ,userService.updatePassword(token,update));
    }

    @PostMapping("/change-username")
    public ApiResponse<AuthResponse> changeUserName(@RequestHeader("Authorization") String token,
                                                    @RequestBody UpdateUserNameRequest newUserName){
        logger.info("hitting the request change username");
        return new ApiResponse<>(true, "user name changed"
                ,userService.updateUserName(token,newUserName));
    }

    @PostMapping("/change-email")
    public ApiResponse<UserResponse> changeEmailAddress(@RequestHeader("Authorization") String token,
                                                    @RequestBody String newEmail){
        logger.info("hitting the request change email address");
        return new ApiResponse<>(true, "user name changed"
                ,userService.updateEmail(token,newEmail));
    }

}
