package com.genz.socio.service;


import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.enums.Role;
import com.genz.socio.dto.request.LoginRequest;
import com.genz.socio.dto.request.RegisterRequest;
import com.genz.socio.dto.response.AuthResponse;
import com.genz.socio.exception.BadRequestException;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.mapper.UserMapper;
import com.genz.socio.repo.UserRepository;
import com.genz.socio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findUserByEmailOrUserName(request.getEmailOrUserName())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Credentials"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new ResourceNotFoundException("Invalid Credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token,userMapper.toResponse(user));
    }

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request){
        if (userRepository.findUserByEmailOrUserName(request.getEmailOrPhone()).isPresent()) {
            throw new BadRequestException("User already exists");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setEmailOrPhone(request.getEmailOrPhone());
        user.setFullName(request.getFullName());

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, userMapper.toResponse(user));
    }
}