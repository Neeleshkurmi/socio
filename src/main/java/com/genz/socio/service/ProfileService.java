package com.genz.socio.service;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JwtService jwtService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProfileResponse follow(User following, String token) {
        String userName = jwtService.extractUserName(token);

        User  user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();
        profile.getFollowing().add(following);
        profileRepository.save(profile);

        Profile profile1 = following.getProfile();
        profile1.getFollowers().add(user);
        profileRepository.save(profile1);

        return modelMapper.map(profile,ProfileResponse.class);
    }

    public ProfileResponse getProfile(String token) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        Profile profile = user.getProfile();

        return modelMapper.map(profile,ProfileResponse.class);
    }
}
