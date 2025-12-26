package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/")
    public ApiResponse<ProfileResponse> getProfile(@RequestHeader String token){
        System.out.println("DEBUG inside follow");
        return new ApiResponse<>(true,"user profile",profileService.getProfile(token));
    }

    @PostMapping
    public ApiResponse<ProfileResponse> follow(@RequestHeader String token, @RequestBody User following){
        System.out.println("DEBUG inside follow");
        return new ApiResponse<>(true,"user profile",profileService.follow(following,token));
    }
}
