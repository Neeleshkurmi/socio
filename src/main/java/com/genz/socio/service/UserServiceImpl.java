package com.genz.socio.service;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.exception.ResourceNotFoundException;
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
    public AuthResponse updateUserName(String token, UpdateUserNameRequest update) {
        String userName = jwtService.extractUserName(token);
        System.out.println("error is after this line");

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        if(user!=null){
            user.setUserName(update.getUserName());
            userRepository.save(user);
            String newToken = jwtService.generateToken(user);
            return new AuthResponse(newToken, userMapper.toResponse(user));
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public UpdatePassword updatePassword(String token, UpdatePasswordRequest update) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        if(passwordEncoder.matches(update.getPrePassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(update.getNewPassword()));
            userRepository.save(user);
            return new UpdatePassword(true,"password updated",userMapper.toResponse(user));
        }
        throw new ResourceNotFoundException("Invalid Credentials");
    }

    @Override
    public UserResponse updateEmail(String token, String email) {
        String userName = jwtService.extractUserName(token);

        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        if(user!=null){
            user.setUserName(email);
            userRepository.save(user);
            return userMapper.toResponse(user);
        }
        throw  new UsernameNotFoundException("User Not Found");
    }

    @Override
    public UserResponse follow(String token, User user) {
        String userName = jwtService.extractUserName(token);

        User me = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("user not found"));

        if(me!=null){
            //TODO
            profileService.follow(user);
            return userMapper.toResponse(me);
        }
        throw  new UsernameNotFoundException("User Not Found");
    }
}
