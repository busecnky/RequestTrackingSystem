package com.requesttrackingsystem.service;

import com.requesttrackingsystem.dto.request.LoginRequestDto;
import com.requesttrackingsystem.dto.request.RegisterRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;
import com.requesttrackingsystem.entity.User;

public interface UserService {
    JwtResponseDto register(RegisterRequestDto request);
    JwtResponseDto login(LoginRequestDto request);
    User findByUsername(String username);
}