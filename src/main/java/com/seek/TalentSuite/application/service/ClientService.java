package com.seek.TalentSuite.application.service;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    ClientDtoResponse createClient(ClientDtoRequest clientDtoRequest);

    ClientDtoResponse updateClient(ClientDtoRequest clientDtoRequest, Long id);

    ClientsMetrics getMetrics();
    Page<ClientDtoResponse> getClientsData(Pageable pageable);

    void deleteClient(Long id);


}
