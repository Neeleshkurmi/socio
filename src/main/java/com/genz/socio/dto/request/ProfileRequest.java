package com.genz.socio.dto.request;

import com.genz.socio.dto.enums.Title;
import lombok.Data;


@Data
public class ProfileRequest {
    private String profilePhoto;
    private String bio;
    private String location;
    private Title title;
}
