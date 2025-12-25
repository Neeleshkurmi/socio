package com.genz.socio.service;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.mapper.UserMapper;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Override
    public UserResponse updateUserName(String token, String newUserName) {
        String userName = jwtService.extractUserName(token);
        System.out.println("error is after this line");

        User user = userRepository.findByUserName(userName);

        if(user!=null){
            user.setUserName(newUserName);
            return userMapper.toResponse(user);
        }
        throw  new UsernameNotFoundException("User Not Found");
    }

    @Override
    public UpdatePassword updatePassword(String token, String prePassword, String newPassword) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName);

        if(passwordEncoder.matches(prePassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            return new UpdatePassword(true,"password updated",userMapper.toResponse(user));
        }
        return new UpdatePassword(false, "incorrect password",userMapper.toResponse(user));
    }

    @Override
    public UserResponse updateEmail(String token, String email) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName);

        if(user!=null){
            user.setUserName(email);
            return userMapper.toResponse(user);
        }
        throw  new UsernameNotFoundException("User Not Found");
    }

    @Override
    public UserResponse follow(String token, User user) {
        String userName = jwtService.extractUserName(token);

        User me = userRepository.findByUserName(userName);

        if(me!=null){
            //TODO
            profileService.follow(user);
            return userMapper.toResponse(me);
        }
        throw  new UsernameNotFoundException("User Not Found");
    }
}
