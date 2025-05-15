package com.requesttrackingsystem.utils;

import com.requesttrackingsystem.entity.User;
import com.requesttrackingsystem.entity.enums.Role;
import com.requesttrackingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartupRunnerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StartupRunner startupRunner;

    @Test
    void shouldCreateAdminUserIfNotExists() throws Exception {
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("admin123")).thenReturn("encodedPassword");

        startupRunner.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("admin@example.com", savedUser.getEmail());
        assertEquals("admin", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Role.ADMIN, savedUser.getRole());
    }

    @Test
    void shouldNotCreateAdminUserIfExists() throws Exception {
        User existingAdmin = new User();
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(existingAdmin));

        startupRunner.run();

        verify(userRepository, never()).save(any());
    }
}

