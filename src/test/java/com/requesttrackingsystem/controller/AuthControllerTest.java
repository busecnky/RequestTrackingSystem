package com.requesttrackingsystem.controller;

import com.requesttrackingsystem.dto.request.LoginRequestDto;
import com.requesttrackingsystem.dto.request.RegisterRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;
import com.requesttrackingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Test
    void testRegister() {
        RegisterRequestDto dto = new RegisterRequestDto("test", "test@email.com", "password");
        JwtResponseDto expectedResponse = new JwtResponseDto("jwt-token");

        when(userService.register(dto)).thenReturn(expectedResponse);

        JwtResponseDto response = authController.register(dto);

        assertEquals(expectedResponse, response);
        verify(userService).register(dto);
    }

    @Test
    void testLogin() {
        LoginRequestDto dto = new LoginRequestDto("test", "password");
        JwtResponseDto expectedResponse = new JwtResponseDto("jwt-token");

        when(userService.login(dto)).thenReturn(expectedResponse);

        JwtResponseDto response = authController.login(dto);

        assertEquals(expectedResponse, response);
        verify(userService).login(dto);
    }
}

