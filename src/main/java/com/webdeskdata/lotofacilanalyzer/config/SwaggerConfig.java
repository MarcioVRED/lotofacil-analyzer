package com.webdeskdata.lotofacilanalyzer.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI lotofacilOpenAPI() {

        return new OpenAPI()

                .info(
                        new Info()
                                .title("Lotofácil Analyzer API")
                                .version("v1")
                                .description("""
                                        API responsável por:

                                        - sincronização de concursos
                                        - geração de CSV
                                        - geração de Excel
                                        - estatísticas da Lotofácil
                                        - análise de repetições
                                        - verificação de jogos
                                        - geração de dataset para IA
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

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local")
                ))

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Documentação Spring")
                                .url("https://spring.io")
                );
    }
}