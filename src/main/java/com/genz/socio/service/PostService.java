package com.genz.socio.service;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.EditPostRequest;
import com.genz.socio.dto.response.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    PostResponse createPost(PostRequest post, User user);

    String deletePost(Long id, User user);

    PostResponse editPost(EditPostRequest post);

}
