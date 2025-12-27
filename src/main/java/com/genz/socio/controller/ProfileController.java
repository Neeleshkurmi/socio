package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.ProfileRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.FollowerListResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ApiResponse<ProfileResponse> getProfile(@RequestHeader("Authorization") String token){
        return new ApiResponse<>(true,"user profile",profileService.getProfile(token));
    }

    @PostMapping("/profile-changes")
    public ApiResponse<ProfileResponse> profileChanges(@RequestHeader("Authorization") String token,
                                                       @RequestBody ProfileRequest request){
        return new ApiResponse<>(true,"changes successfully updated",profileService.profileChanges(token, request));
    }

    public ApiResponse<ProfileResponse> createProfile(@RequestHeader("Authorization") String token,
                                                      @RequestBody ProfileRequest request){
        return new ApiResponse<>(true,"user profile created",profileService.createProfile(token,request));
    }

    @PostMapping("/follow/{id}")
    public ApiResponse<ProfileResponse> follow(@RequestHeader("Authorization") String token, @PathVariable Long id){
        return new ApiResponse<>(true,"user profile",profileService.follow(id,token));
    }

    @GetMapping("/get-followers")
    public ApiResponse<FollowerListResponse> getAllFollowers(@RequestHeader("Authorization") String token){
        return new ApiResponse<>(true, "followers list", profileService.getAllFollowers(token));
    }

    @GetMapping("/get-followings")
    public ApiResponse<FollowerListResponse> getAllFollowings(@RequestHeader("Authorization") String token){
        return new ApiResponse<>(true, "followers list", profileService.getAllFollowing(token));
    }
}
