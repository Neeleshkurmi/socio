package com.genz.socio.dto.request;

import com.genz.socio.dto.response.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostRequest {
    Long id;
    String url;
    String title;
    LocalDateTime createdAt;
    Long noLikes;
    Long noComments;
    Long noShares;
    ProfileResponse author;
}
