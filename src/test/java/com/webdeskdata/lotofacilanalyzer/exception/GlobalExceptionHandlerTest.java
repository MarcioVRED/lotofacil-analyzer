package com.webdeskdata.lotofacilanalyzer.exception;

import com.webdeskdata.lotofacilanalyzer.dto.ApiResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIllegalArgumentRetornaErroPadronizado() {

        MockHttpServletRequest request =
                new MockHttpServletRequest("POST", "/api/lotofacil/sync");

        ResponseEntity<ApiResponseDTO<Void>> response =
                handler.handleIllegalArgument(
                        new IllegalArgumentException("dezenas inválidas"),
                        request
                );

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sucesso()).isFalse();
        assertThat(response.getBody().mensagem()).isEqualTo("Requisição inválida.");
        assertThat(response.getBody().path()).isEqualTo("/api/lotofacil/sync");
        assertThat(response.getBody().erro().codigo()).isEqualTo("BAD_REQUEST");
        assertThat(response.getBody().erro().detalhes()).containsExactly("dezenas inválidas");
    }

    @Test
    void handleRestClientRetornaBadGatewayPadronizado() {

        MockHttpServletRequest request =
                new MockHttpServletRequest("GET", "/api/lotofacil/stats");

        ResponseEntity<ApiResponseDTO<Void>> response =
                handler.handleRestClient(
                        new RestClientException("timeout"),
                        request
                );

        assertThat(response.getStatusCode().value()).isEqualTo(502);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sucesso()).isFalse();
        assertThat(response.getBody().erro().codigo()).isEqualTo("EXTERNAL_SERVICE_ERROR");
        assertThat(response.getBody().erro().detalhes()).containsExactly("timeout");
    }
}
