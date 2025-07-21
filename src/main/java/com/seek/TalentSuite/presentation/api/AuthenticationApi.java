package com.seek.TalentSuite.presentation.api;

import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import com.seek.TalentSuite.application.dto.response.AuthenticatedDto;
import com.seek.TalentSuite.application.dto.response.RegisteredDto;
import com.seek.TalentSuite.application.exception.custom.ApplicationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "Authentication", description = "API for user authentication and registration")
@RequestMapping(path ="/auth",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface AuthenticationApi {

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided registration details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Registration data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = RegisteredDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid registration data",content = @Content(schema = @Schema(implementation = ApplicationError.class)))
            }
    )
    @PostMapping("/signup")
    ResponseEntity<RegisteredDto> register(@Valid @RequestBody RegisterDto registerDto);

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token if credentials are valid.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                            content = @Content(schema = @Schema(implementation = AuthenticatedDto.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",content = @Content(schema = @Schema(implementation = ApplicationError.class)))
            }
    )
    @PostMapping("/login")
    ResponseEntity<AuthenticatedDto> login(@Valid @RequestBody LoginDto loginDto);
}
