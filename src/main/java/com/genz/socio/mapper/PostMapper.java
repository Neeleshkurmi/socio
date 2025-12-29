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
        response.setNoOfPosts((long) post.getAuthor().getPosts().size());
        response.setNoLikes((long)post.getLikedBy().size());
        response.setNoComments((long)post.getComments().size());
        response.setNoShares(post.getShares());
        response.setUrl(post.getPostUrl());

        return response;
    }
}
