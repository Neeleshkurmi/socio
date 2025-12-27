package com.genz.socio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerAndFollowingResponse {
    Long id;
    String useName;
    String profilePhoto;
}
