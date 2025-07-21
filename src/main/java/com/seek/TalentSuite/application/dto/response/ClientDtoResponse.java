package com.seek.TalentSuite.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Response DTO for client data")
public class ClientDtoResponse{
    @Schema(description = "Unique identifier of the client", example = "1")
    private Long id;

    @Schema(description = "Client's first name", example = "John")
    private String name;

    @Schema(description = "Client's last name", example = "Doe")
    private String lastName;

    @Schema(description = "Client's age", example = "30")
    private Integer age;

    @Schema(description = "Client's date of birth", example = "1993-05-15T00:00:00.000+00:00")
    private LocalDate dateOfBirth;

    @Schema(description = "Timestamp when the data was retrieved", example = "2024-06-01T12:00:00")
    private LocalDate retrievedAt;

    @Schema(description = "Estimated time to retire", example = "Jon has 35 years left to retire")
    private String timeToRetire;
}
