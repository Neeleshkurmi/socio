package com.genz.socio.mapper;

import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.response.PostResponse;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PostMapper {

    private final ProfileMapper mapper = new ProfileMapper();

    public PostResponse toResponse(Post post){
        PostResponse response = new PostResponse();

        response.setId(post.getId());
        response.setAuthor(mapper.toResponse(post.getAuthor().getProfile(),post.getAuthor()));
        response.setCreatedAt(post.getCreatedAt());
        response.setTitle(post.getTitle());
        response.setLikes(post.getLikedBy());
        response.setComments(post.getComments());
        response.setNoShares(post.getShares());
        response.setUrl(post.getPostUrl());
        return response;
    }
}
