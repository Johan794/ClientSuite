package com.seek.TalentSuite.unit.application.service.impl;


import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.dto.response.PageResponse;
import com.seek.TalentSuite.application.exception.custom.ApplicationException;
import com.seek.TalentSuite.application.mapper.ClientMapper;
import com.seek.TalentSuite.application.service.MetricsService;
import com.seek.TalentSuite.application.service.impl.ClientServiceImpl;
import com.seek.TalentSuite.persistence.entity.Client;
import com.seek.TalentSuite.persistence.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Spy
    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private MetricsService metricsService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void createClient_shouldSaveAndReturnResponse() {
        ClientDtoRequest request = mock(ClientDtoRequest.class);
        when(request.name()).thenReturn("John");
        when(clientRepository.getClientByName("John")).thenReturn(Optional.empty());
        Client client = new Client();
        when(clientMapper.clientDtoRequestToClient(request)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        ClientDtoResponse response = new ClientDtoResponse();
        when(clientMapper.clientToClientDtoResponse(client)).thenReturn(response);

        ClientDtoResponse result = clientService.createClient(request);

        assertEquals(response, result);
        verify(clientRepository).save(client);
    }

    @Test
    void createClient_shouldThrowIfClientExists() {
        ClientDtoRequest request = mock(ClientDtoRequest.class);
        when(request.name()).thenReturn("John");
        when(clientRepository.getClientByName("John")).thenReturn(Optional.of(new Client()));

        assertThrows(ApplicationException.class, () -> clientService.createClient(request));
    }

    @Test
    void updateClient_shouldUpdateAndReturnResponse() {
        Long id = 1L;
        ClientDtoRequest request = mock(ClientDtoRequest.class);
        when(request.name()).thenReturn("Jane");
        when(request.lastName()).thenReturn("Doe");
        when(request.age()).thenReturn(30);
        when(request.dateOfBirth()).thenReturn("07/11/2002");
        Client client = new Client();
        when(clientRepository.getClientById(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        ClientDtoResponse response = new ClientDtoResponse();
        when(clientMapper.clientToClientDtoResponse(client)).thenReturn(response);

        ClientDtoResponse result = clientService.updateClient(request, id);

        assertEquals(response, result);
        verify(clientRepository).save(client);
    }

    @Test
    void updateClient_shouldThrowIfNotFound() {
        Long id = 1L;
        ClientDtoRequest request = mock(ClientDtoRequest.class);
        when(clientRepository.getClientById(id)).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> clientService.updateClient(request, id));
    }

    @Test
    void getMetrics_shouldReturnMetrics() {
        ClientsMetrics metrics = new ClientsMetrics();
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());
        when(metricsService.calculateMetrics(anyList())).thenReturn(metrics);

        ClientsMetrics result = clientService.getMetrics();

        assertEquals(metrics, result);
    }

    @Test
    void getClientsData_shouldReturnPageResponse() {
        Pageable pageable = PageRequest.of(0, 1);
        Client client = new Client();
        ClientDtoResponse dto = new ClientDtoResponse();
        Page<Client> clientPage = new PageImpl<>(Collections.singletonList(client), pageable, 1);


        when(clientRepository.findAll(pageable)).thenReturn(clientPage);
        when(clientMapper.clientToClientDtoResponse(client)).thenReturn(dto);
        doReturn(dto).when(clientService).calculateAgeToRetire(dto);

        PageResponse<ClientDtoResponse> result = clientService.getClientsData(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
    }

    @Test
    void getClientsData_shouldThrowIfEmpty() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Client> clientPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(clientRepository.findAll(pageable)).thenReturn(clientPage);

        assertThrows(ApplicationException.class, () -> clientService.getClientsData(pageable));
    }

    @Test
    void deleteClient_shouldCallRepository() {
        Long id = 1L;
        when(clientRepository.getClientById(id)).thenReturn(Optional.of(new Client()));
        doNothing().when(clientRepository).deleteById(id);

        clientService.deleteClient(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    void deleteClient_shouldThrowIfNotFound(){
        Long id = 2L;
        when(clientRepository.getClientById(id)).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> clientService.deleteClient(id));

        verify(clientRepository,never()).deleteById(id);
    }


}
