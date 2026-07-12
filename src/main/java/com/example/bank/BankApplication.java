package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
/*
 * @ComponentScans({ @ComponentScan("com.example.bank") })
 * 
 * @EnableJpaRepositories("com.example.bank.repository")
 * 
 * @EntityScan("com.example.bank.model")
 */
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Accounts microservice REST API Documentation", description = "Bank Accounts microservice REST API Documentation", version = "v1", contact = @Contact(name = "Abhishek D", email = "tutor@account.com", url = "https://www.account.com"), license = @License(name = "Apache 2.0", url = "https://www.account.com")), externalDocs = @ExternalDocumentation(description = "Bank Accounts microservice REST API Documentation", url = "https://www.account.com/swagger-ui.html"))
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
