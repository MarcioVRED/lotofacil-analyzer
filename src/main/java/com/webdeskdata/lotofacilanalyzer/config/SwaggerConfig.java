package com.webdeskdata.lotofacilanalyzer.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI lotofacilOpenAPI() {

        return new OpenAPI()

                .info(
                        new Info()
                                .title("Lotofácil Analyzer API")
                                .version("1.0.0")
                                .description("""
                                        API para:
                                        - sincronização da Lotofácil
                                        - geração CSV
                                        - geração Excel
                                        - estatísticas
                                        - dataset para IA
                                        """)
                                .contact(
                                        new Contact()
                                                .name("Marcio Martins")
                                                .email("marcio@email.com")
                                )
                                .license(
                                        new License()
                                                .name("MIT")
                                )
                )

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Documentação Spring")
                                .url("https://spring.io")
                );
    }
}