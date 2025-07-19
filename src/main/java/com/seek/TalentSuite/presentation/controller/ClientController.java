package com.seek.TalentSuite.presentation.controller;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.service.ClientService;
import com.seek.TalentSuite.presentation.api.ClientsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientsApi {
    private final ClientService clientService;

    @Override
    public ResponseEntity<ClientDtoResponse> createClient(ClientDtoRequest clientDtoRequest) {
        return ResponseEntity.ok(clientService.createClient(clientDtoRequest));
    }

    @Override
    public ResponseEntity<ClientDtoResponse> updateClient(ClientDtoRequest clientDtoRequest, Long id) {
        return ResponseEntity.ok(clientService.updateClient(clientDtoRequest, id));
    }

    @Override
    public ResponseEntity<ClientsMetrics> getMetrics() {
        return ResponseEntity.ok(clientService.getMetrics());
    }

    @Override
    public Page<ClientDtoResponse> getClientsData(Pageable pageable) {
        return clientService.getClientsData(pageable);
    }

    @Override
    public ResponseEntity<Void> deleteClient(Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
