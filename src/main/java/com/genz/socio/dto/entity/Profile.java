package com.genz.socio.dto.entity;

import com.genz.socio.dto.enums.Title;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Profile extends BaseEntity {

    private String profilePhoto;
    private String bio;

    @Enumerated(EnumType.STRING)
    private Title title;

    private String location;

    @ManyToMany
    @JoinTable(
            name = "profile_followers",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<Profile> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<Profile> following = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}