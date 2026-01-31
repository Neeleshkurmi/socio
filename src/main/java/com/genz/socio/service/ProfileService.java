package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.enums.Title;
import com.genz.socio.dto.request.ProfileRequest;
import com.genz.socio.dto.response.FollowerAndFollowingResponse;
import com.genz.socio.dto.response.FollowerListResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.mapper.FollowerAndFollowingMapper;
import com.genz.socio.mapper.ProfileMapper;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FollowerAndFollowingMapper followingMapper;
    private final ProfileMapper profileMapper;

    @Transactional
    @CachePut(cacheNames = "userprofile", key = "#userName")
    public ProfileResponse followAndUnfollow(Long id, String userName) {
        User user1 = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile to followAndUnfollow not exists"));

        Profile u1Profile = user1.getProfile();
        Profile u2Profile = user2.getProfile();

        if (u1Profile.getFollowing() == null) u1Profile.setFollowing(new HashSet<>());
        if (u2Profile.getFollowers() == null) u2Profile.setFollowers(new HashSet<>());


        if (u1Profile.getFollowing().contains(u2Profile)) {
            u2Profile.getFollowers().remove(u1Profile);
            u1Profile.getFollowing().remove(u2Profile);
        } else {
            u2Profile.getFollowers().add(u1Profile);
            u1Profile.getFollowing().add(u2Profile);
        }

        profileRepository.save(u2Profile);
        profileRepository.save(u1Profile);

        return profileMapper.toResponse(u1Profile,user1);
    }

    @Cacheable(cacheNames = "userprofile", key = "#userName")
    public ProfileResponse getProfile(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        return profileMapper.toResponse(profile,user);
    }

    @Transactional
    @CachePut(cacheNames = "userprofile", key = "#userName")
    public ProfileResponse createProfile(String userName, ProfileRequest request) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setProfilePhoto(request.getProfilePhoto());
        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setTitle(Title.PERSONAL);
        profile.setUser(user);

        return profileMapper.toResponse(profile,user);
    }

    @Cacheable(cacheNames = "followers", key = "#userName")
    public FollowerListResponse getAllFollowers(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<Profile> followers= profile.getFollowers();

        Set<FollowerAndFollowingResponse> followSet = new HashSet<>();
        for (Profile follower : followers) {
            followSet.add(followingMapper.toResponse(follower.getUser(),follower));
        }
        return new FollowerListResponse(followSet);
    }

    @Cacheable(cacheNames = "following", key = "#userName")
    public FollowerListResponse getAllFollowing(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<Profile> following= profile.getFollowing();

        Set<FollowerAndFollowingResponse> followingSet = new HashSet<>();
        for (Profile follower : following) {
            followingSet.add(followingMapper.toResponse(follower.getUser(),follower));
        }
        return new FollowerListResponse(followingSet);
    }

    @CachePut(cacheNames = "userprofile", key = "#userName")
    public ProfileResponse profileChanges(String userName, ProfileRequest request) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setBio(request.getBio().isBlank() ? profile.getBio() : request.getBio());
        profile.setProfilePhoto(request.getProfilePhoto().isBlank() ? profile.getProfilePhoto() : request.getProfilePhoto());
        profile.setLocation(request.getLocation().isBlank() ? profile.getLocation() : request.getLocation());
        profile.setTitle(request.getTitle().isBlank() ? profile.getTitle() : Title.valueOf(request.getTitle().toUpperCase(Locale.ROOT)));

        profileRepository.save(profile);

        return profileMapper.toResponse(profile,user);
    }
}
