package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.enums.Title;
import com.genz.socio.dto.request.ProfileRequest;
import com.genz.socio.dto.response.FollowerListResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JwtService jwtService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ProfileResponse follow(Long id, String token) {
        String userName = jwtService.extractUserName(token);

        User user1 = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile to follow does not exist"));

        Profile u1Profile = user1.getProfile();
        Profile u2Profile = user2.getProfile();

        if (u1Profile.getFollowing() == null) u1Profile.setFollowing(new HashSet<>());
        if (u2Profile.getFollowers() == null) u2Profile.setFollowers(new HashSet<>());

        if (!u1Profile.getFollowing().contains(user2)) {
            u1Profile.getFollowing().add(user2);
            u2Profile.getFollowers().add(user1);

            profileRepository.save(u1Profile);
            profileRepository.save(u2Profile);
        }

        ProfileResponse response = modelMapper.map(u1Profile, ProfileResponse.class);
        response.setFollowersCount(u1Profile.getFollowers() != null ? (long) u1Profile.getFollowers().size() : 0L);
        response.setFollowingCount(u1Profile.getFollowing() != null ? (long) u1Profile.getFollowing().size() : 0L);

        return response;
    }

    public ProfileResponse getProfile(String token) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        ProfileResponse response = modelMapper.map(profile, ProfileResponse.class);

        response.setFollowersCount(profile.getFollowers() != null ? (long) profile.getFollowers().size() : 0L);
        response.setFollowingCount(profile.getFollowing() != null ? (long) profile.getFollowing().size() : 0L);

        return response;
    }

    @Transactional
    public ProfileResponse createProfile(String token, ProfileRequest request) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setProfilePhoto(request.getProfilePhoto());
        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setTitle(Title.PERSONAL);
        profile.setUser(user);

        return modelMapper.map(profile,ProfileResponse.class);
    }

    public FollowerListResponse getAllFollowers(String token) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<User> followers= profile.getFollowers();

        return modelMapper.map(followers,FollowerListResponse.class);
    }

    public FollowerListResponse getAllFollowing(String token) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<User> following = profile.getFollowing();

        return modelMapper.map(following,FollowerListResponse.class);
    }

    public ProfileResponse profileChanges(String token, ProfileRequest request) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setBio(request.getBio().isBlank() ? profile.getBio() : request.getBio());
        profile.setProfilePhoto(request.getProfilePhoto().isBlank() ? profile.getProfilePhoto() : request.getProfilePhoto());
        profile.setLocation(request.getLocation().isBlank() ? profile.getLocation() : request.getLocation());
        profile.setTitle(request.getTitle().isBlank() ? profile.getTitle() : Title.valueOf(request.getTitle().toUpperCase(Locale.ROOT)));

        profileRepository.save(profile);

        return modelMapper.map(profile,ProfileResponse.class);
    }
}
