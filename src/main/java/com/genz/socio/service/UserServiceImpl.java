package com.genz.socio.service;

import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.UpdateEmailRequest;
import com.genz.socio.dto.request.UpdatePasswordRequest;
import com.genz.socio.dto.request.UpdateUserNameRequest;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.dto.response.ProfileResponse;
import com.genz.socio.dto.response.UpdatePassword;
import com.genz.socio.dto.response.UserResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.mapper.UserMapper;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Override
    public AuthResponse updateUserName(String userName, UpdateUserNameRequest update) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("Resource not found"));

        if(user!=null){
            user.setUserName(update.getUserName());
            userRepository.save(user);
            String newToken = jwtService.generateToken(user);
            return new AuthResponse(newToken, userMapper.toResponse(user));
        }
        throw new UsernameNotFoundException("user Not Found");
    }

    @Override
    public UpdatePassword updatePassword(String userName, UpdatePasswordRequest update) {
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
    public UserResponse updateEmail(String userName, UpdateEmailRequest newEmail) {
      User user = userRepository.findByUserName(userName).orElseThrow(()
                                                    -> new ResourceNotFoundException("user not found"));

      user.setEmailOrPhone(newEmail.getEmail());
      userRepository.save(user);
      return modelMapper.map(user,UserResponse.class);
    }
}
