package com.genz.socio.dto.request;

import lombok.Data;


@Data
public class ProfileRequest {
    private String profilePhoto;
    private String bio;
    private String location;
    private String title;
}
