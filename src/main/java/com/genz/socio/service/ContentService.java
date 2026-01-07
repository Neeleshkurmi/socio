package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.dto.response.ProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentService {

    List<PostResponse> homeContent(User user);

    List<PostResponse> explore(User user);

    ProfileResponse search(String username);

    List<PostResponse> shortVideos(User user);

    PostResponse getLatestPosts(Profile profile);
}
