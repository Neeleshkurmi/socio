package com.genz.socio.mapper;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.response.ProfileResponse;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ProfileMapper {

    public ProfileResponse toResponse(Profile profile, String username){
        return new ProfileResponse(username,profile.getProfilePhoto(),profile.getBio()
                ,profile.getTitle().toString(), profile.getLocation(),profile.getNoOfPosts()
                ,profile.getFollowers() != null ? (long) profile.getFollowers().size() : 0L
                ,profile.getFollowing() != null ? (long) profile.getFollowing().size() : 0L);
    }
}