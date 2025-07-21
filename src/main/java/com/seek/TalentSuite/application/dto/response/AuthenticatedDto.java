package com.seek.TalentSuite.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Schema(description = "Response DTO containing authentication details")
@Builder
@Data
public class AuthenticatedDto {
    @Schema(description = "Authenticated user's email address", example = "user@example.com")
   private String email;

    @Schema(description = "Authenticated user's Jwt")
    private String jwt;

    @Schema(description = "Date when the user was authenticated", example = "2024-06-01")
    private LocalDate authenticatedAt;
}
