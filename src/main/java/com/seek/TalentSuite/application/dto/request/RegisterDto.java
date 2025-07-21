package com.seek.TalentSuite.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
@Schema(description = "Request DTO for user registration")
public record RegisterDto(
        @NotBlank
        @Schema(description = "User's email address", example = "user@example.com")
        String email,

        @NotBlank
        @Schema(description = "User's password", example = "P@ssw0rd123")
        String password
) {
}
