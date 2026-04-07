package com.backend.handicrafts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tribalHandicraftsOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Tribal Handicrafts API")
                .description("REST API documentation for the Tribal Handicrafts platform")
                .version("v1")
                .contact(new Contact().name("FSAD Team").email("support@haandicrafts.local"))
                .license(new License().name("Internal Academic Use")));
    }
}