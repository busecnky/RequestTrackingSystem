package com.requesttrackingsystem.service.impl;

import com.requesttrackingsystem.dto.request.LoginRequestDto;
import com.requesttrackingsystem.dto.request.RegisterRequestDto;
import com.requesttrackingsystem.dto.response.JwtResponseDto;
import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.repository.UserRepository;
import com.requesttrackingsystem.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegister() {
        RegisterRequestDto request = new RegisterRequestDto("john", "john@email.com", "pass");
        String encodedPassword = "encodedPass";
        String token = "jwt-token";

        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);
        when(jwtUtil.generateToken(any(User.class))).thenReturn(token);

        JwtResponseDto response = userService.register(request);

        assertEquals(token, response.token);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        LoginRequestDto request = new LoginRequestDto("john", "pass");
        User user = new User();
        user.setUsername("john");
        user.setPassword("encodedPass");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "encodedPass")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        JwtResponseDto result = userService.login(request);

        assertEquals("jwt-token", result.token);
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequestDto request = new LoginRequestDto("invalid", "pass");
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.login(request));
    }

    @Test
    void testLogin_WrongPassword() {
        LoginRequestDto request = new LoginRequestDto("john", "wrongpass");
        User user = new User();
        user.setPassword("encodedPass");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encodedPass")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.login(request));
    }

    @Test
    void testFindByUsername_UserExists() {
        User user = new User();
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");

        assertEquals(user, result);
    }

    @Test
    void testFindByUsername_NotFound() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findByUsername("notfound"));
    }
}

