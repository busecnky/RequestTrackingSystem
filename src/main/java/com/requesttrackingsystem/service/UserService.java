package com.requesttrackingsystem.service;

import com.requesttrackingsystem.dto.request.JwtRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;

public interface UserService {
    JwtResponseDto register(JwtRequestDto request);
    JwtResponseDto login(JwtRequestDto request);
}