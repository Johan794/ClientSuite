package com.seek.TalentSuite.presentation.controller.util;

import com.seek.TalentSuite.persistence.entity.User;
import com.seek.TalentSuite.persistence.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestDataConfig {
    @Bean
    public ApplicationRunner dataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder()
                    .email("test@example.com")
                    .password(passwordEncoder.encode("password"))
                    .build();
            userRepository.save(user);
        };
    }
}
