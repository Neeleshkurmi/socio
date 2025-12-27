package com.genz.socio.mapper;

import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.FollowerAndFollowingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerAndFollowingMapper {
    Long id;
    String userName;
    String profilePhoto;

    public FollowerAndFollowingResponse toResponse(User user, Profile profile){
        return new FollowerAndFollowingResponse(user.getId(),user.getUserName(),profile.getProfilePhoto());
    }
}
