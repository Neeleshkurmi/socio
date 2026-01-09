package com.genz.socio.controller;

import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.CommentRequest;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.EditPostRequest;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.mapper.UserMapper;
import com.genz.socio.repo.PostRepository;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import com.genz.socio.service.PostService;
import com.genz.socio.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserMapper modelMapper;

    @PostMapping("/create-post")
    public ApiResponse<PostResponse> createPost(@AuthenticationPrincipal UserPrincipal userDetails
                                                , @RequestBody PostRequest request){
        User user = modelMapper.map(userDetails);

        return new ApiResponse<>(true, "post uploaded successfully",postService.createPost(request, user));
    }

    @DeleteMapping("/delete-post/{id}")
    public ApiResponse<String> deletePost(@AuthenticationPrincipal UserPrincipal userDetails
            , @PathVariable Long id){
        User user = modelMapper.map(userDetails);

        return new ApiResponse<>(true, "post deleted successfully",postService.deletePost(id,user));
    }

    @PutMapping("/edit-post")
    public ApiResponse<PostResponse> editPost(@AuthenticationPrincipal UserPrincipal userDetails
            , @RequestBody EditPostRequest request){
        User user = modelMapper.map(userDetails);

        return new ApiResponse<>(true, "post edit successfully", postService.editPost(request, user));
    }

    @PutMapping("/like-post/{postId}/{userId}")
    public ApiResponse<PostResponse> likePost(@AuthenticationPrincipal UserPrincipal userDetails,
                                              @PathVariable Long postId, @PathVariable Long userId){

        User likedBy = modelMapper.map(userDetails);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found"));

        return new ApiResponse<>(true, "you have liked the post", postService.likeOrDislikePost(likedBy, post));
    }

    @PutMapping("/comment-post/{postId}/{userId}")
    public ApiResponse<PostResponse> commentOnPost(@AuthenticationPrincipal UserPrincipal userDetails,
                                                   @PathVariable Long postId, @PathVariable Long userId,
                                                   @RequestBody CommentRequest comment){

        User commentBy = modelMapper.map(userDetails);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found"));

        return new ApiResponse<>(true, "you have liked the post", postService.commentOnPost(commentBy,
                post, comment));
    }

    @GetMapping("/get-post/{postId}")
    public ApiResponse<PostResponse> getPost(@AuthenticationPrincipal UserPrincipal userDetails,
                                                              @PathVariable Long postId){
        User user = modelMapper.map(userDetails);

       return  new ApiResponse<>(true, "here is your post", postService.getPost(postId,user));
    }

    @GetMapping("/get-all/posts")
    public ApiResponse<List<PostResponse>> getAllPosts(@AuthenticationPrincipal UserPrincipal userDetails){
        User user = modelMapper.map(userDetails);
        return  new ApiResponse<>(true, "here is your post", postService.getAllPosts(user));
    }

    @PutMapping("/share-post/{postId}")
    public ApiResponse<PostResponse> sharePost(@AuthenticationPrincipal UserPrincipal userDetails,
                                               @PathVariable Long postId){
        User user = new User();

        return new ApiResponse<>(true, "post shared successfully", postService.sharePost(user, postId));
    }
}
