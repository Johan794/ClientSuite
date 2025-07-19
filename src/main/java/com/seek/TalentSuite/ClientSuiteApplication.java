package com.seek.TalentSuite;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "TalentSuite microservice REST API Documentation",
				description = "Clients management System",
				version = "v1",
				contact = @Contact(
						name = "Johan Ricardo",
						email = "johan.ricardo1104@gmail.com",
						url = "https://github.com/Johan794"
				)
		)
)
@SpringBootApplication
public class ClientSuiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSuiteApplication.class, args);
	}

}
