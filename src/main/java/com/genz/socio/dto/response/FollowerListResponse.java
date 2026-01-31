package com.genz.socio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerListResponse {
    Set<FollowerAndFollowingResponse> followersList;

    public Set<FollowerAndFollowingResponse> getFollowers() {
        return followersList;
    }
}
