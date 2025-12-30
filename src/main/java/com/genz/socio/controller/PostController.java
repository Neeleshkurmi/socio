package com.genz.socio.controller;

import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.CommentRequest;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.EditPostRequest;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.repo.PostRepository;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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

    @PutMapping("/edit-post")
    public ApiResponse<PostResponse> editPost(@RequestHeader("Authorization") String token
            , @RequestBody EditPostRequest request){
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new UsernameNotFoundException("user not found, login again and then try"));

        return new ApiResponse<>(true, "post edit successfully", postService.editPost(request, user));
    }

    @PutMapping("/like-post/{postId}/{userId}")
    public ApiResponse<PostResponse> likePost(@RequestHeader("Authorization") String token,
                                              @PathVariable Long postId, @PathVariable Long userId){

        User likedBy  = userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("user not find"));

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found"));

        return new ApiResponse<>(true, "you have liked the post", postService.likeOrDislikePost(likedBy, post));
    }

    @PutMapping("/comment-post/{postId}/{userId}")
    public ApiResponse<PostResponse> commentOnPost(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long postId, @PathVariable Long userId,
                                                   @RequestBody CommentRequest comment){

        User likedBy  = userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("user not find"));

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found"));

        return new ApiResponse<>(true, "you have liked the post", postService.commentOnPost(likedBy,
                post, comment));
    }

    @GetMapping("/get-post/{postId}")
    public ApiResponse<PostResponse> getPost(@RequestHeader("Authorization") String token,
                                             @PathVariable Long postId){
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new UsernameNotFoundException("user not found, login again and then try"));

       return  new ApiResponse<>(true, "here is your post", postService.getPost(postId));
    }
}
