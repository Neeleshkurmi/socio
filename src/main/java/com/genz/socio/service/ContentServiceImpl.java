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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final String cacheKey = "#user.userName";

    @Override
    @Cacheable(cacheNames = "home", key = "#user.userName")
    public List<PostResponse> homeContent(User user) {
        Set<Profile> followings = user.getProfile().getFollowing();

        Pageable pageable = PageRequest.of(0, 20);

        return postRepository.findFeedByFollowing(followings, pageable)
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    @Cacheable(cacheNames ="explore", key = "user.userName")
    public List<PostResponse> explore(User user) {
        return List.of();
    }

    @Override
    @Cacheable(cacheNames = "search", key = "username")
    public List<UserResponse> search(String username) {

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> usersPage = userRepository.findByUserNameContaining(username, pageable);

        List<User> users = usersPage.getContent();

        List<UserResponse> result = new ArrayList<>();

        for(User user: users){
            result.add(userMapper.toResponse(user));
        }
        return result;
    }

    @Override
    @Cacheable(cacheNames = "shorts", key = cacheKey)
    public List<PostResponse> shortVideos(User user) {
        return List.of();
    }

    @Override
    @CachePut(cacheNames = "home", key = cacheKey)
    public PostResponse getLatestPosts(Profile profile) {
        return postService.getLatestPosts(profile.getUser(), LocalDateTime.now());
    }
}
