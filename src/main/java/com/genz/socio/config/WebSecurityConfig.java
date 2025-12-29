package com.genz.socio.config;

import com.genz.socio.mapper.FollowerAndFollowingMapper;
import com.genz.socio.mapper.PostMapper;
import com.genz.socio.mapper.ProfileMapper;
import com.genz.socio.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(c->c.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**","/api/health/check","/",
                                "/login", "/signup", "/profile-view","/edit-profile").permitAll()
                        .requestMatchers("/api/user/profile/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(se->se
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FollowerAndFollowingMapper followingMapper(){
        return new FollowerAndFollowingMapper();
    }

    @Bean
    public ProfileMapper profileMapper(){
        return new ProfileMapper();
    }

    @Bean
    public PostMapper postMapper(){
        return new PostMapper();
    }

}
