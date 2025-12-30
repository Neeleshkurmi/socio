package com.genz.socio.mapper;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.ProfileResponse;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ProfileMapper {

    public ProfileResponse toResponse(Profile profile, User user){
        return new ProfileResponse(user.getId(), user.getUserName(),profile.getProfilePhoto(),profile.getBio()
                ,profile.getTitle().toString(), (long) user.getPosts().size(), profile.getLocation()
                ,profile.getFollowers() != null ? (long) profile.getFollowers().size() : 0L
                ,profile.getFollowing() != null ? (long) profile.getFollowing().size() : 0L);
    }
}