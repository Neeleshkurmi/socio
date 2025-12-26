package com.genz.socio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String profilePhoto;
    private String bio;
    private String title;
    private String location;
    private Long noOfPosts;
    private Long followersCount;
    private Long followingCount;
}
