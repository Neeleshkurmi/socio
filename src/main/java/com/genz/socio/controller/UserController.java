package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.UpdateEmailRequest;
import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.UserService;
import com.genz.socio.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private Logger logger = Logger.getLogger("logger");
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PatchMapping("/change-password")
    public ApiResponse<UpdatePassword> changePassword(@RequestHeader("Authorization") String token,
                                                      @RequestBody UpdatePasswordRequest update){
        String userName = jwtService.extractUserName(token);

        return new ApiResponse<>(true,"password updated successfully"
                ,userService.updatePassword(userName,update));
    }

    @PatchMapping("/change-username")
    public ApiResponse<AuthResponse> changeUserName(@RequestHeader("Authorization") String token,
                                                    @RequestBody UpdateUserNameRequest newUserName){
        String userName = jwtService.extractUserName(token);

        return new ApiResponse<>(true, "user name changed"
                ,userService.updateUserName(userName,newUserName));
    }

    @PatchMapping("/change-email")
    public ApiResponse<UserResponse> changeEmailAddress(@RequestHeader("Authorization") String token,
                                                        @RequestBody UpdateEmailRequest newEmail){
        String userName = jwtService.extractUserName(token);

        return new ApiResponse<>(true, "email changed"
                ,userService.updateEmail(userName,newEmail));
    }

    //TODO
//    @PostMapping
//    public void post(@RequestHeader String token, @RequestBody PostRequest postRequest){
//
//    }

}
