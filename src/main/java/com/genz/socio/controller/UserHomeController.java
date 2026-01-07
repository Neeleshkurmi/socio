package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserHomeController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ContentService contentService;

    @GetMapping("/home")
    public ApiResponse<List<PostResponse>> homeContent(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(username).orElseThrow(()->
                    new ResourceNotFoundException("user not found"));

        return new ApiResponse<>(true, "random home content", contentService.homeContent(user));
    }

    @PostMapping("/search")
    public ApiResponse<ProfileResponse> search(@RequestHeader("Authorization") String token){
        //TODO
        return null;
    }
}
