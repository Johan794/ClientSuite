package com.seek.TalentSuite.application.mapper;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.persistence.entity.Client;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public interface ClientMapper {

    ClientDtoResponse clientToClientDtoResponse(Client client);


    Client clientDtoRequestToClient(ClientDtoRequest clientDtoRequest);

    static LocalDate stringToLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date format. Use 'dd/MM/yyyy', for example: '07/11/2002'.");
        }
    }

}
