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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FollowerAndFollowingMapper followingMapper;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileService profileService;

    private User createUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUserName(username);

        Profile profile = new Profile();
        profile.setFollowers(new HashSet<>());
        profile.setFollowing(new HashSet<>());
        profile.setUser(user);

        user.setProfile(profile);
        return user;
    }

    @Test
    void getProfile_shouldReturnProfileResponse_whenUserExists() {
        User user = createUser(1L, "neelesh");
        ProfileResponse response = new ProfileResponse();

        when(userRepository.findByUserName("neelesh"))
                .thenReturn(Optional.of(user));
        when(profileMapper.toResponse(user.getProfile(), user))
                .thenReturn(response);

        ProfileResponse result = profileService.getProfile("neelesh");

        assertNotNull(result);
        verify(userRepository).findByUserName("neelesh");
        verify(profileMapper).toResponse(user.getProfile(), user);
    }

    @Test
    void getProfile_shouldThrowException_whenUserNotFound() {
        when(userRepository.findByUserName("ghost"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> profileService.getProfile("ghost"));
    }


    @Test
    void followAndUnfollow_shouldFollow_whenNotFollowing() {
        User user1 = createUser(1L, "user1");
        User user2 = createUser(2L, "user2");

        ProfileResponse response = new ProfileResponse();

        when(userRepository.findByUserName("user1"))
                .thenReturn(Optional.of(user1));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user2));
        when(profileMapper.toResponse(user1.getProfile(), user1))
                .thenReturn(response);

        ProfileResponse result =
                profileService.followAndUnfollow(2L, "user1");

        assertTrue(user1.getProfile().getFollowing().contains(user2.getProfile()));
        assertTrue(user2.getProfile().getFollowers().contains(user1.getProfile()));

        verify(profileRepository).save(user1.getProfile());
        verify(profileRepository).save(user2.getProfile());
    }

    @Test
    void followAndUnfollow_shouldUnfollow_whenAlreadyFollowing() {
        User user1 = createUser(1L, "user1");
        User user2 = createUser(2L, "user2");

        // already following
        user1.getProfile().getFollowing().add(user2.getProfile());
        user2.getProfile().getFollowers().add(user1.getProfile());

        when(userRepository.findByUserName("user1"))
                .thenReturn(Optional.of(user1));
        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user2));
        when(profileMapper.toResponse(any(), any()))
                .thenReturn(new ProfileResponse());

        profileService.followAndUnfollow(2L, "user1");

        assertFalse(user1.getProfile().getFollowing().contains(user2.getProfile()));
        assertFalse(user2.getProfile().getFollowers().contains(user1.getProfile()));
    }


    @Test
    void createProfile_shouldUpdateProfileFields() {
        User user = createUser(1L, "neelesh");

        ProfileRequest request = new ProfileRequest();
        request.setBio("Developer");
        request.setLocation("India");
        request.setProfilePhoto("photo.png");

        when(userRepository.findByUserName("neelesh"))
                .thenReturn(Optional.of(user));
        when(profileMapper.toResponse(any(), any()))
                .thenReturn(new ProfileResponse());

        ProfileResponse response =
                profileService.createProfile("neelesh", request);

        Profile profile = user.getProfile();

        assertEquals("Developer", profile.getBio());
        assertEquals("India", profile.getLocation());
        assertEquals("photo.png", profile.getProfilePhoto());
        assertEquals(Title.PERSONAL, profile.getTitle());
    }

    @Test
    void profileChanges_shouldUpdateOnlyNonBlankFields() {
        User user = createUser(1L, "neelesh");
        Profile profile = user.getProfile();

        profile.setBio("Old Bio");
        profile.setLocation("Old Location");
        profile.setProfilePhoto("old.png");
        profile.setTitle(Title.PERSONAL);

        ProfileRequest request = new ProfileRequest();
        request.setBio("");
        request.setLocation("New Location");
        request.setProfilePhoto("");
        request.setTitle("business");

        when(userRepository.findByUserName("neelesh"))
                .thenReturn(Optional.of(user));
        when(profileMapper.toResponse(any(), any()))
                .thenReturn(new ProfileResponse());

        profileService.profileChanges("neelesh", request);

        assertEquals("Old Bio", profile.getBio());
        assertEquals("New Location", profile.getLocation());
        assertEquals("old.png", profile.getProfilePhoto());
        assertEquals(Title.BUSINESS, profile.getTitle());
    }


    @Test
    void getAllFollowers_shouldReturnFollowerList() {
        User user = createUser(1L, "user1");
        User follower = createUser(2L, "user2");

        user.getProfile().getFollowers().add(follower.getProfile());

        when(userRepository.findByUserName("user1"))
                .thenReturn(Optional.of(user));
        when(followingMapper.toResponse(any(), any()))
                .thenReturn(new FollowerAndFollowingResponse());

        FollowerListResponse response =
                profileService.getAllFollowers("user1");

        assertEquals(1, response.getFollowers().size());
    }


}
