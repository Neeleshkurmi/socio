package com.genz.socio.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genz.socio.dto.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String emailOrPhone;

    private String password;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isVerified;
}
