package com.genz.socio.service;


import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.LoginRequest;
import com.genz.socio.dto.request.RegisterRequest;
import com.genz.socio.dto.response.ApiResponse;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<AuthResponse> getUser(LoginRequest request){
        User user = userRepository.getUserByEmailOrUserNameAndPassword(request.getEmailOrUserName(), request.getPassword());
        if(user!=null){
            return new ApiResponse<>(true,"Signup Successful",modelMapper.map(user,AuthResponse.class));
        }
        return new ApiResponse<>(false,"user not found", modelMapper.map(request, AuthResponse.class));
    }

    @Override
    public ApiResponse<AuthResponse> saveUser(RegisterRequest request){
        User user = userRepository.save(modelMapper.map(request,User.class));
        return new ApiResponse<>(true,"user saved",modelMapper.map(user,AuthResponse.class));
    }
}