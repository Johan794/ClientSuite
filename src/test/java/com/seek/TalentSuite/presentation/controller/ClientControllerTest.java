package com.seek.TalentSuite.presentation.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.seek.TalentSuite.application.dto.request.ClientDtoRequest;
import com.seek.TalentSuite.application.dto.response.ClientDtoResponse;
import com.seek.TalentSuite.application.dto.response.ClientsMetrics;
import com.seek.TalentSuite.application.dto.response.PageResponse;
import com.seek.TalentSuite.application.service.ClientService;
import com.seek.TalentSuite.presentation.controller.util.Configurations;
import com.seek.TalentSuite.presentation.controller.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import static com.seek.TalentSuite.presentation.controller.util.Utils.BASE_URL_CLIENTS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
@Import(Configurations.class)
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;


    @Test
    @WithMockUser
    void test_createClient_Authenticated() throws Exception{
        mockMvc.perform(post(BASE_URL_CLIENTS+"/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.createClientExample))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldUpdateClient() throws Exception {
        ClientDtoRequest request = new ClientDtoRequest("Juan", "Perez", 30, "01/01/1990");
        ClientDtoResponse response = ClientDtoResponse.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .age(30)
                .dateOfBirth(LocalDate.parse("01/01/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();


        given(clientService.updateClient(any(), eq(1L))).willReturn(response);

        mockMvc.perform(put(BASE_URL_CLIENTS+"/update/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"));
    }

    @Test
    @WithMockUser
    void shouldGetMetrics() throws Exception {
        ClientsMetrics metrics = ClientsMetrics.builder()
                .totalClients(100)
                .averageAge(38.111111111111114)
                .minAge(18)
                .maxAge(66)
                .medianAge(45)
                .modeAge(45)
                .stdDevAge(14.79447678053795)
                .varianceAge(218.87654320987656)
                .build();


        given(clientService.getMetrics()).willReturn(metrics);

        mockMvc.perform(get(BASE_URL_CLIENTS+"/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalClients").value(100));
    }

    @Test
    @WithMockUser
    void shouldGetClientsData() throws Exception {
        Page<ClientDtoResponse> page = new PageImpl<>(
                List.of(ClientDtoResponse.builder()
                        .id(1L)
                        .name("Juan")
                        .lastName("Perez")
                        .age(30)
                        .dateOfBirth(LocalDate.parse("01/01/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build())
        );

        given(clientService.getClientsData(any())).willReturn(new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        ));

        mockMvc.perform(get(BASE_URL_CLIENTS)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Juan"));
    }

    @Test
    @WithMockUser
    void shouldDeleteClient() throws Exception {
        willDoNothing().given(clientService).deleteClient(1L);

        mockMvc.perform(delete(BASE_URL_CLIENTS+"/delete/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_createClient_Forbidden() throws Exception {
        mockMvc.perform(post(BASE_URL_CLIENTS + "/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.createClientExample))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUpdateClient_Forbidden() throws Exception {
        ClientDtoRequest request = new ClientDtoRequest("Juan", "Perez", 30, "01/01/1990");
        mockMvc.perform(put(BASE_URL_CLIENTS + "/update/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetMetrics_Forbidden() throws Exception {
        mockMvc.perform(get(BASE_URL_CLIENTS + "/metrics"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetClientsData_Forbidden() throws Exception {
        mockMvc.perform(get(BASE_URL_CLIENTS)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldDeleteClient_Forbidden() throws Exception {
        mockMvc.perform(delete(BASE_URL_CLIENTS + "/delete/1").with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}

