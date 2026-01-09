package com.genz.socio.dto.entity;

import com.genz.socio.dto.enums.Role;
import com.genz.socio.security.UserPrincipal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email or phone is required")
    private String emailOrPhone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Name is required")
    private String fullName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "User Name must not be empty")
    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isVerified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


    public void addPost(Post post) {
        posts.add(post);
        post.setAuthor(this);
    }

}
