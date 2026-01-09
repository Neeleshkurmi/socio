package com.genz.socio.controller;

import com.genz.socio.dto.request.ProfileRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.FollowerListResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;

    @GetMapping("/")
    public ApiResponse<ProfileResponse> getProfile(@RequestHeader("Authorization") String token){
        String userName = jwtService.extractUserName(token);
        return new ApiResponse<>(true,"user profile",profileService.getProfile(userName));
    }

    @PostMapping("/profile-changes")
    public ApiResponse<ProfileResponse> profileChanges(@RequestHeader("Authorization") String token,
                                                       @RequestBody ProfileRequest request){
        String userName = jwtService.extractUserName(token);

        return new ApiResponse<>(true,"changes successfully updated",profileService.profileChanges(userName, request));
    }

    public ApiResponse<ProfileResponse> createProfile(@RequestHeader("Authorization") String token,
                                                      @RequestBody ProfileRequest request){
        String userName = jwtService.extractUserName(token);
        return new ApiResponse<>(true,"user profile created",profileService.createProfile(userName,request));
    }

    @PostMapping("/followAndUnfollow/{id}")
    public ApiResponse<ProfileResponse> followAndUnfollow(@RequestHeader("Authorization") String token, @PathVariable Long id){
        String userName = jwtService.extractUserName(token);
        return new ApiResponse<>(true,"user profile",profileService.followAndUnfollow(id,userName));
    }

    @GetMapping("/get-followers")
    public ApiResponse<FollowerListResponse> getAllFollowers(@RequestHeader("Authorization") String token){
        String userName = jwtService.extractUserName(token);
        return new ApiResponse<>(true, "followers list", profileService.getAllFollowers(userName));
    }

    @GetMapping("/get-followings")
    public ApiResponse<FollowerListResponse> getAllFollowings(@RequestHeader("Authorization") String token){
        String userName = jwtService.extractUserName(token);
        return new ApiResponse<>(true, "following list", profileService.getAllFollowing(userName));
    }
}
