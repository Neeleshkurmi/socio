package com.genz.socio.service;

import com.genz.socio.dto.entity.Comment;
import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.CommentRequest;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.EditPostRequest;
import com.genz.socio.dto.response.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostResponse createPost(PostRequest post, User user);

    String deletePost(Long id, User user);

    PostResponse editPost(EditPostRequest post, User user);

    PostResponse likeOrDislikePost(User likedBy , Post post);

    PostResponse commentOnPost(User likedBy, Post post, CommentRequest comment);

    PostResponse getPost(Long postId,  User user);

    List<PostResponse> getAllPosts(User user);

    PostResponse sharePost(User user, Long postId);
}
