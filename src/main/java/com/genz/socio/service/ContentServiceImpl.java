package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.mapper.PostMapper;
import com.genz.socio.repo.PostRepository;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final PostServiceImpl postService;
    private final PostMapper postMapper;

    @Override
    public List<PostResponse> homeContent(User user) {

        Set<Profile> followings = user.getProfile().getFollowing();

        List<PostResponse> posts = new ArrayList<>();

        for(Profile following : followings){
            if(posts.size()<=9){
                posts.add(getLatestPosts(following));
            }
        }
        return posts;
    }

    @Override
    public List<PostResponse> explore(User user) {
        return List.of();
    }

    @Override
    public List<UserResponse> search(User user) {
        return List.of();
    }

    @Override
    public List<PostResponse> shortVideos(User user) {
        return List.of();
    }

    @Override
    public PostResponse getLatestPosts(Profile profile) {
        return postService.getLatestPosts(profile.getUser(), LocalDateTime.now());
    }
}
