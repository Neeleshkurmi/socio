package com.genz.socio.dto.response;

import com.genz.socio.dto.entity.Post;
import com.genz.socio.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    Long id;
    String url;
    String title;
    Long noOfPosts;
    LocalDateTime createdAt;
    Long noLikes;
    Long noComments;
    Long noShares;
    ProfileResponse author;
}
