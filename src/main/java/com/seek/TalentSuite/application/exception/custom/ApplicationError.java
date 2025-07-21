package com.seek.TalentSuite.application.exception.custom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Structured error response containing HTTP status and error details.")
public class ApplicationError {
    @Schema(description = "The HTTP status of the error.", example = "BAD_REQUEST")
    private HttpStatus status;
    @Schema(description = "List of detailed error information.")
    private List<ErrorDetail> details;

}
