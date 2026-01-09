package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ApiResponse<List<PostResponse>> homeContent(@RequestHeader("Authorization") String token,
                                                       @RequestParam(defaultValue = "0") int page){

        String username = jwtService.extractUserName(token);
        User user = userRepository.findUserByEmailOrUserName(username).orElseThrow(()->
                new ResourceNotFoundException("login again"));

        return new ApiResponse<>(true, "random home content", contentService.homeContent(user));
    }

    @GetMapping("/search")
    public ApiResponse<Page<User>> search(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> results = userRepository.findByUserNameContaining(username, pageable);

        return new ApiResponse<>(true, "Search results", results);
    }
}
