package com.seek.TalentSuite.presentation.api;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.dto.response.PageResponse;
import com.seek.TalentSuite.application.exception.custom.ApplicationError;
import com.seek.TalentSuite.application.exception.custom.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Clients", description = "API for managing clients")
@RequestMapping( path ="/clients",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface ClientsApi {
    @Operation(
            summary = "Create a new client",
            description = "Creates a new client with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client created successfully",
                            content = @Content(schema = @Schema(implementation = ClientDtoResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content(schema = @Schema(implementation = ApplicationError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - not allowed")
            }
    )
    @PostMapping("/create")
  ResponseEntity<ClientDtoResponse>  createClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client data to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClientDtoRequest.class))
            )
            @Valid @RequestBody ClientDtoRequest clientDtoRequest);


    @Operation(
            summary = "Update an existing client",
            description = "Updates the client identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client updated successfully",
                            content = @Content(schema = @Schema(implementation = ClientDtoResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found",content = @Content(schema = @Schema(implementation = ApplicationError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - not allowed")
            }
    )

    @PutMapping("/update/{id}")
ResponseEntity<ClientDtoResponse>  updateClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated client data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClientDtoRequest.class))
            )
            @Valid @RequestBody  ClientDtoRequest clientDtoRequest,
            @Parameter(description = "ID of the client to update", required = true)
            @PathVariable Long id);


    @Operation(
            summary = "Get client metrics",
            description = "Retrieves metrics for all clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Metrics retrieved",
                            content = @Content(schema = @Schema(implementation = ClientsMetrics.class))),
                    @ApiResponse(responseCode = "404", description = "Clients not found",content = @Content(schema = @Schema(implementation = ApplicationError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - not allowed")
            }
    )
    @GetMapping("/metrics")
   ResponseEntity<ClientsMetrics>  getMetrics();

    @Operation(
            summary = "Get paginated list of clients",
            description = "Retrieves a paginated list of clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients data retrived",
                            content = @Content(schema = @Schema(implementation = PageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content(schema = @Schema(implementation = ApplicationError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - not allowed")
            }
    )
    @GetMapping
    PageResponse<ClientDtoResponse> getClientsData(
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sorting criteria in the format: property(,asc|desc).", example = "name,asc") @RequestParam(required = false) String sort
    );


    @Operation(
            summary = "Delete a client",
            description = "Deletes the client identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Client deleted"),
                    @ApiResponse(responseCode = "404", description = "Client not found",content = @Content(schema = @Schema(implementation = ApplicationError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - not allowed")
            }
    )
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteClient(
            @Parameter(description = "ID of the client to delete", required = true)
            @PathVariable Long id
    );

}
