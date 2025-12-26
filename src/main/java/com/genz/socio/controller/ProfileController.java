package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.ProfileRequest;
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
    public ApiResponse<ProfileResponse> getProfile(@RequestHeader("Authorization") String token){
        System.out.println("DEBUG inside follow");
        return new ApiResponse<>(true,"user profile",profileService.getProfile(token));
    }

    @PostMapping("/create")
    public ApiResponse<ProfileResponse> createProfile(@RequestHeader("Authorization") String token,
                                                      @RequestBody ProfileRequest request){
        return new ApiResponse<>(true,"user profile created",profileService.createProfile(token,request));
    }

    @PostMapping("/follow/{id}")
    public ApiResponse<ProfileResponse> follow(@RequestHeader("Authorization") String token, @PathVariable Long id){
        System.out.println("DEBUG inside follow");
        return new ApiResponse<>(true,"user profile",profileService.follow(id,token));
    }
}
