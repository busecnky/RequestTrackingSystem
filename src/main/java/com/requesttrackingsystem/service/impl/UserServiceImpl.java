package com.requesttrackingsystem.service.impl;

import com.requesttrackingsystem.dto.request.JwtRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.repository.UserRepository;
import com.requesttrackingsystem.utils.JwtUtil;
import com.requesttrackingsystem.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponseDto register(JwtRequestDto request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setRole(User.Role.USER);
        userRepository.save(user);
        return new JwtResponseDto(jwtUtil.generateToken(user));
    }

    @Override
    public JwtResponseDto login(JwtRequestDto request) {
        User user = userRepository.findByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        return new JwtResponseDto(jwtUtil.generateToken(user));
    }
}
