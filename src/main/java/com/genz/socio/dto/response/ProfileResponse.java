package com.genz.socio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String userName;
    private String profilePhoto;
    private String bio;
    private String title;
    private Long noOfPosts;
    private String location;
    private Long followersCount;
    private Long followingCount;
}
