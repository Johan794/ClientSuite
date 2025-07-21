package com.seek.TalentSuite.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.seek.TalentSuite.presentation.controller.util.Utils.BASE_URL_AUTH;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturn200_whenValidInput() throws Exception {
        RegisterDto dto = new RegisterDto("newuser@example.com", "password");

        mockMvc.perform(post(BASE_URL_AUTH+"/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    void login_shouldReturnJwt_whenUserExists() throws Exception {
        LoginDto dto = new LoginDto("test@example.com", "password");

        mockMvc.perform(post(BASE_URL_AUTH+"/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists());
    }

    @Test
    void register_Fails_EmptyFields() throws Exception {
        RegisterDto registerDto = RegisterDto.builder().build();
        mockMvc.perform(post(BASE_URL_AUTH+"/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_Fails_EmptyFields() throws Exception {
        LoginDto loginDto = LoginDto.builder().build(); // all fields empty
        mockMvc.perform(post(BASE_URL_AUTH+"/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest());
    }
    

}
