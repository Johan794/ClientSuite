package com.seek.TalentSuite.unit.application.service.impl.auth;

import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import com.seek.TalentSuite.application.dto.response.RegisteredDto;
import com.seek.TalentSuite.application.service.auth.AuthServiceImpl;
import com.seek.TalentSuite.persistence.entity.User;
import com.seek.TalentSuite.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Spy
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void signup_shouldRegisterNewUser() {
        RegisterDto registerDto = new RegisterDto("test@email.com", "pass");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        User savedUser = User.builder().email("test@email.com").password("encodedPass").build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisteredDto result = authService.signup(registerDto);

        assertEquals(savedUser.getUsername(), result.email());
        assertEquals(LocalDate.now(), result.registeredAt());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signup_shouldThrowIfUserExists() {
        RegisterDto registerDto = new RegisterDto("exists@email.com", "pass");
        when(userRepository.findByEmail("exists@email.com")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.signup(registerDto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void authenticate_shouldReturnUserIfCredentialsValid() {
        LoginDto loginDto = new LoginDto("user@email.com", "pass");
        User user = User.builder().email("user@email.com").password("encodedPass").build();
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        User result = authService.authenticate(loginDto);

        assertEquals(user, result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticate_shouldThrowIfUserNotFound() {
        LoginDto loginDto = new LoginDto("nouser@email.com", "pass");
        when(userRepository.findByEmail("nouser@email.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.authenticate(loginDto));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }


}
