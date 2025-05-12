package com.requesttrackingsystem.controller;

import com.requesttrackingsystem.dto.request.JwtRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;
import com.requesttrackingsystem.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public JwtResponseDto register(@RequestBody JwtRequestDto request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto request) {
        return userService.login(request);
    }
}

