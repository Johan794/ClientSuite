package com.seek.TalentSuite.presentation.controller.util;
import com.seek.TalentSuite.application.service.AuthService;
import com.seek.TalentSuite.application.service.ClientService;
import com.seek.TalentSuite.application.service.auth.JWTService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;


@TestConfiguration
public class Configurations {
    @Bean
    public JWTService jwtService() {
        return Mockito.mock(JWTService.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    public ClientService clientService(){
        return Mockito.mock(ClientService.class);
    }
    @Bean
   public AuthService authService(){
        return Mockito.mock(AuthService.class);
    }

}
