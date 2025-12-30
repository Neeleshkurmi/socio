package com.genz.socio.dto.response;

import com.genz.socio.dto.entity.Comment;
import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.Profile;
import com.genz.socio.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostResponse {
    Long id;
    String url;
    String title;
    LocalDateTime createdAt;
    Set<Profile> likes = new HashSet<>();
    List<Comment> comments = new ArrayList<>();
    Long noShares;
    ProfileResponse author;
}
