package com.seek.TalentSuite.application.service.auth;

import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import com.seek.TalentSuite.application.dto.response.RegisteredDto;
import com.seek.TalentSuite.application.service.AuthService;
import com.seek.TalentSuite.persistence.entity.User;
import com.seek.TalentSuite.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisteredDto signup(RegisterDto registerDto) {
        verifyIsUserExists(registerDto.email());
        User user = User.builder()
                .email(registerDto.email())
                .password(passwordEncoder.encode(registerDto.password()))
                .build();
        userRepository.save(user);
        return new RegisteredDto(user.getUsername(), LocalDate.now());
    }

    @Override
    public User authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        return userRepository.findByEmail(loginDto.email()).orElseThrow(RuntimeException::new);
    }

    private void verifyIsUserExists(String email){
        userRepository.findByEmail(email).ifPresent( __-> {
            throw new RuntimeException(String.format("The user with email %s already exists",email));
        });
    }
}
