package com.seek.TalentSuite.presentation.api;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "Clients", description = "API for managing clients")
@RequestMapping("/clients")
public interface ClientsApi {
    @Operation(
            summary = "Create a new client",
            description = "Creates a new client with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client created successfully",
                            content = @Content(schema = @Schema(implementation = ClientDtoResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
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
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )

    @PutMapping("/update/{id}")
ResponseEntity<ClientDtoResponse>  updateClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated client data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClientDtoRequest.class))
            )
            ClientDtoRequest clientDtoRequest,
            @Parameter(description = "ID of the client to update", required = true)
            @PathVariable Long id);


    @Operation(
            summary = "Get client metrics",
            description = "Retrieves metrics for all clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Metrics retrieved",
                            content = @Content(schema = @Schema(implementation = ClientsMetrics.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @GetMapping("/metrics")
   ResponseEntity<ClientsMetrics>  getMetrics();

    @Operation(
            summary = "Get paginated list of clients",
            description = "Retrieves a paginated list of clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clients retrieved",
                            content = @Content(schema = @Schema(implementation = ClientDtoResponse.class)))
            }
    )
    @GetMapping
    Page<ClientDtoResponse> getClientsData(
            @Parameter(description = "Pagination information") @PageableDefault(size = 10) Pageable pageable
    );


    @Operation(
            summary = "Delete a client",
            description = "Deletes the client identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Client deleted"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteClient(
            @Parameter(description = "ID of the client to delete", required = true)
            @PathVariable Long id
    );

}
