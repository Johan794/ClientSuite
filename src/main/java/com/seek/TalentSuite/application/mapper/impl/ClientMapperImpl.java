package com.seek.TalentSuite.application.mapper.impl;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.mapper.ClientMapper;
import com.seek.TalentSuite.persistence.entity.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class ClientMapperImpl implements ClientMapper {
    @Override
    public ClientDtoResponse clientToClientDtoResponse(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDtoResponse clientDtoResponse = new ClientDtoResponse();

        clientDtoResponse.setId( client.getId() );
        clientDtoResponse.setName( client.getName() );
        clientDtoResponse.setLastName( client.getLastName() );
        clientDtoResponse.setAge( client.getAge() );
        if ( client.getDateOfBirth() != null ) {
            clientDtoResponse.setDateOfBirth(client.getDateOfBirth());
        }

        clientDtoResponse.setRetrievedAt(LocalDate.now());

        return clientDtoResponse;
    }

    @Override
    public Client clientDtoRequestToClient(ClientDtoRequest clientDtoRequest) {
        if ( clientDtoRequest == null ) {
            return null;
        }

        Client client = new Client();

        client.setDateOfBirth(ClientMapper.stringToLocalDate( clientDtoRequest.dateOfBirth() ) );
        client.setName( clientDtoRequest.name() );
        client.setLastName( clientDtoRequest.lastName() );
        client.setAge( clientDtoRequest.age() );

        return client;
    }
}
