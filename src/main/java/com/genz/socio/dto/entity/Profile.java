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

    @ManyToMany
    @JoinTable(
            name = "profile_followers",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers;

    @ManyToMany
    @JoinTable(
            name = "profile_following",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> following;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
