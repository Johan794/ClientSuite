package com.seek.TalentSuite.application.service.impl;

import com.seek.TalentSuite.application.constants.AppConstants;
import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.exception.custom.ApplicationError;
import com.seek.TalentSuite.application.exception.custom.ApplicationException;
import com.seek.TalentSuite.application.exception.custom.ErrorCode;
import com.seek.TalentSuite.application.mapper.ClientMapper;
import com.seek.TalentSuite.application.service.ClientService;
import com.seek.TalentSuite.application.service.MetricsService;
import com.seek.TalentSuite.persistence.entity.Client;
import com.seek.TalentSuite.persistence.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.seek.TalentSuite.application.exception.util.ErrorManager.createDetail;
import static com.seek.TalentSuite.application.exception.util.ErrorManager.sendDetails;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final MetricsService metricsService;
    private final ClientMapper clientMapper;


    @Override
    public ClientDtoResponse createClient(ClientDtoRequest clientDtoRequest) {
        validateIfClientExists(clientDtoRequest.name());
        Client clientToCreate = clientMapper.clientDtoRequestToClient(clientDtoRequest);
        return clientMapper.clientToClientDtoResponse(clientRepository.save(clientToCreate));
    }

    @Override
    public ClientDtoResponse updateClient(ClientDtoRequest clientDtoRequest, Long id) {
        Client clientToUpdate = clientRepository.getClientById(id).orElseThrow( createApplicationException("Client not found",String.format("The client with ID %s was not found",id),ErrorCode.ERROR_CLIENT_NOT_FOUND,HttpStatus.NOT_FOUND));

        clientToUpdate.setName(clientDtoRequest.name());
        clientToUpdate.setLastName(clientDtoRequest.lastName());
        clientToUpdate.setAge(clientDtoRequest.age());
        clientToUpdate.setDateOfBirth(ClientMapper.stringToLocalDate(clientDtoRequest.dateOfBirth()));

        return clientMapper.clientToClientDtoResponse(clientRepository.save(clientToUpdate));
    }

    @Override
    public ClientsMetrics getMetrics() {
        return metricsService.calculateMetrics(clientRepository.findAll());
    }

    @Override
    public Page<ClientDtoResponse> getClientsData(Pageable pageable) {
        Page<ClientDtoResponse>  clients = clientRepository.findAll(pageable).map(clientMapper::clientToClientDtoResponse);
        if(clients.isEmpty()){
            throw createApplicationException("There are no clients","There are no clients to retrieve its data",ErrorCode.ERROR_NO_CLIENTS,HttpStatus.NOT_FOUND).get();
        }
        return  clients.map(this::calculateAgeToRetire);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
    private void validateIfClientExists(String clientName){
        clientRepository.getClientByName(clientName).ifPresent( client -> {
            throw createApplicationException("This client already exists",String.format("The client with the name %s is already created",clientName),ErrorCode.ERROR_CLIENT,HttpStatus.BAD_REQUEST).get();
        });
    }

    protected ClientDtoResponse calculateAgeToRetire(ClientDtoResponse clientDtoResponse){
        int leftToRetire = Math.max(0, AppConstants.AGE_TO_RETIRE - clientDtoResponse.getAge());
        clientDtoResponse.setTimeToRetire(String.format(AppConstants.RETIRE_MESSAGE,clientDtoResponse.getName(),leftToRetire));
        return clientDtoResponse;
    }

    private static Supplier<ApplicationException> createApplicationException(String message, String detail, ErrorCode errorCode, HttpStatus status) {
        return () -> new ApplicationException(message, ApplicationError.builder()
                .details(List.of(sendDetails(createDetail(detail, errorCode))))
                .status(status)
                .build());
    }

}
