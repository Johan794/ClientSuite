package com.seek.TalentSuite.presentation.controller;

import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import com.seek.TalentSuite.application.dto.response.AuthenticatedDto;
import com.seek.TalentSuite.application.dto.response.RegisteredDto;
import com.seek.TalentSuite.application.service.AuthService;
import com.seek.TalentSuite.application.service.auth.JWTService;
import com.seek.TalentSuite.persistence.entity.User;
import com.seek.TalentSuite.presentation.api.AuthenticationApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController implements AuthenticationApi {
    private final AuthService authService;
    private final JWTService jwtService;

    @Override
    public ResponseEntity<RegisteredDto> register(RegisterDto registerDto) {
        log.info("Registering a new user");
        RegisteredDto registeredDto = authService.signup(registerDto);
        return ResponseEntity.ok(registeredDto);
    }

    @Override
    public ResponseEntity<AuthenticatedDto> login(LoginDto loginDto) {
        User authenticatedUser = authService.authenticate(loginDto);
        return ResponseEntity.ok(buildAuthResponse(authenticatedUser,jwtService.generateToken(authenticatedUser)));
    }


    private AuthenticatedDto buildAuthResponse(User user, String jwt){
        return AuthenticatedDto.builder()
                .email(user.getUsername())
                .jwt(jwt)
                .authenticatedAt(LocalDate.now())
                .build();
    }
}
