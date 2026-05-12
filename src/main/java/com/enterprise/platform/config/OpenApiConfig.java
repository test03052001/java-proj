package com.enterprise.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI platformOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enterprise Platform API")
                        .version("v1")
                        .description("Users, catalog, orders, inventory, notifications")
                        .contact(new Contact().name("Platform team")))
                .servers(List.of(new Server().url("/").description("Default")));
    }
}
