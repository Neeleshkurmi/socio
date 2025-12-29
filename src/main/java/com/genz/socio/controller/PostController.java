package com.genz.socio.controller;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.DeletePostRequest;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/create-post")
    public ApiResponse<PostResponse> createPost(@RequestHeader("Authorization") String token
                                                , @RequestBody PostRequest request){
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                                    new UsernameNotFoundException("user not found, login again and then try"));

        return new ApiResponse<>(true, "post uploaded successfully",postService.createPost(request, user));
    }

    @DeleteMapping("/delete-post/{id}")
    public ApiResponse<String> deletePost(@RequestHeader("Authorization") String token
            , @PathVariable Long id){
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new UsernameNotFoundException("user not found, login again and then try"));

        return new ApiResponse<>(true, "post deleted successfully",postService.deletePost(id,user));
    }

}
