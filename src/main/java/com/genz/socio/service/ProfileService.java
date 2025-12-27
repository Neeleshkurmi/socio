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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FollowerAndFollowingMapper followingMapper;
    private final ProfileMapper profileMapper;

    @Transactional
    public ProfileResponse followAndUnfollow(Long id, String userName) {
        User user1 = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User user2 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile to followAndUnfollow not exists"));

        Profile u1Profile = user1.getProfile();
        Profile u2Profile = user2.getProfile();

        if (u1Profile.getFollowing() == null) u1Profile.setFollowing(new HashSet<>());
        if (u2Profile.getFollowers() == null) u2Profile.setFollowers(new HashSet<>());

        if (u1Profile.getFollowing().contains(user2)) {
            u1Profile.getFollowing().remove(user2);
            u2Profile.getFollowers().remove(user1);
        }
        else{
            u1Profile.getFollowing().add(user2);
            u2Profile.getFollowers().add(user1);
        }
        profileRepository.save(u1Profile);
        profileRepository.save(u2Profile);

        return profileMapper.toResponse(u1Profile,user1.getUserName());
    }

    public ProfileResponse getProfile(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        return profileMapper.toResponse(profile,user.getUserName());
    }

    @Transactional
    public ProfileResponse createProfile(String userName, ProfileRequest request) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setProfilePhoto(request.getProfilePhoto());
        profile.setBio(request.getBio());
        profile.setLocation(request.getLocation());
        profile.setTitle(Title.PERSONAL);
        profile.setUser(user);

        return profileMapper.toResponse(profile,user.getUserName());
    }

    public FollowerListResponse getAllFollowers(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<User> followers= profile.getFollowers();

        Set<FollowerAndFollowingResponse> followSet = new HashSet<>();
        for (User follower : followers) {
            followSet.add(followingMapper.toResponse(follower,follower.getProfile()));
        }
        return new FollowerListResponse(followSet);
    }

    public FollowerListResponse getAllFollowing(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        Set<User> following= profile.getFollowing();

        Set<FollowerAndFollowingResponse> followingSet = new HashSet<>();
        for (User follower : following) {
            followingSet.add(followingMapper.toResponse(follower,follower.getProfile()));
        }
        return new FollowerListResponse(followingSet);
    }

    public ProfileResponse profileChanges(String userName, ProfileRequest request) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        profile.setBio(request.getBio().isBlank() ? profile.getBio() : request.getBio());
        profile.setProfilePhoto(request.getProfilePhoto().isBlank() ? profile.getProfilePhoto() : request.getProfilePhoto());
        profile.setLocation(request.getLocation().isBlank() ? profile.getLocation() : request.getLocation());
        profile.setTitle(request.getTitle().isBlank() ? profile.getTitle() : Title.valueOf(request.getTitle().toUpperCase(Locale.ROOT)));

        profileRepository.save(profile);

        return profileMapper.toResponse(profile,user.getUserName());
    }
}
