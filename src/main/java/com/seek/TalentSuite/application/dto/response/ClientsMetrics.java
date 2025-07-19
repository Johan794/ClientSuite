package com.seek.TalentSuite.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Aggregated metrics for all clients")
public class ClientsMetrics {
    @Schema(description = "Total number of clients", example = "100")
    private long totalClients;

    @Schema(description = "Average age of clients", example = "35.5")
    private double averageAge;

    @Schema(description = "Minimum age among clients", example = "18")
    private int minAge;

    @Schema(description = "Maximum age among clients", example = "65")
    private int maxAge;

    @Schema(description = "Median age of clients", example = "34.0")
    private double medianAge;

    @Schema(description = "Most common age among clients", example = "30")
    private int modeAge;

    @Schema(description = "Standard deviation of client ages", example = "8.2")
    private double stdDevAge;

    @Schema(description = "Variance of client ages", example = "67.24")
    private double varianceAge;
}
