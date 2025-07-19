package com.seek.TalentSuite.application.mapper;

import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.persistence.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "timeToRetire", ignore = true)
    @Mapping(target = "retrievedAt", expression = "java(java.time.LocalDateTime.now())")
    ClientDtoResponse clientToClientDtoResponse(Client client);

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", qualifiedByName = "stringToLocalDate")
    Client clientDtoRequestToClient(ClientDtoRequest clientDtoRequest);

    @Named("stringToLocalDate")
    static LocalDate stringToLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date format. Use 'dd/MM/yyyy', for example: '07/11/2002'.");
        }
    }

}
