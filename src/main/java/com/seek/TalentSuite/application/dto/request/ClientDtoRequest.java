package com.seek.TalentSuite.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request DTO for creating or updating a client")
public record ClientDtoRequest(
        @NotBlank
        @Schema(description = "Client's first name", example = "John")
        String name,

        @NotBlank
        @Schema(description = "Client's last name", example = "Doe")
        String lastName,

        @NotNull
        @Schema(description = "Client's age", example = "30")
        Integer age,

        @NotBlank
        @Schema(description = "Client's date of birth in dd/MM/yyyy", example = "07/11/1999")
        String dateOfBirth
) {

}
