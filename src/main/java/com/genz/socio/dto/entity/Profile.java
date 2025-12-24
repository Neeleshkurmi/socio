package com.genz.socio.dto.entity;

import com.genz.socio.dto.enums.Title;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Profile extends BaseEntity{

    private String profilePhoto;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Title title;

    private String location;

    private Long noOfPosts;

    private List<User> followers;

    private List<User> following;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
