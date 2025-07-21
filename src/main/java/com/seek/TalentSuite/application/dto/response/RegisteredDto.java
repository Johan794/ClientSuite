package com.seek.TalentSuite.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


import java.time.LocalDate;

@Builder
@Schema(description = "Response DTO containing registration details")
public record RegisteredDto(
        @Schema(description = "Registered user's email address", example = "user@example.com")
        String email,
        @Schema(description = "Date when the user was registered", example = "2024-06-01")
        LocalDate registeredAt
) {
}
