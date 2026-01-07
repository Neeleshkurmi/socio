package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.mapper.PostMapper;
import com.genz.socio.mapper.ProfileMapper;
import com.genz.socio.mapper.UserMapper;
import com.genz.socio.repo.PostRepository;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final PostServiceImpl postService;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    private final String cacheName = "content";
    private final String cacheKey = "#user.userName";

    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
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
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public List<PostResponse> explore(User user) {
        return List.of();
    }

    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public ProfileResponse search(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(()->
                new UsernameNotFoundException("user not found"));

        return profileMapper.toResponse(user.getProfile(), user);
    }

    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public List<PostResponse> shortVideos(User user) {
        return List.of();
    }

    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public PostResponse getLatestPosts(Profile profile) {
        return postService.getLatestPosts(profile.getUser(), LocalDateTime.now());
    }
}
