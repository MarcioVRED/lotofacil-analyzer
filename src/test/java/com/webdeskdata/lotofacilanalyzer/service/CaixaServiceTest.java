package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class CaixaServiceTest {

    @Test
    void obterConcursoConsultaApiDaCaixaEDesserializaResposta() {

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server =
                MockRestServiceServer.bindTo(restTemplate).build();
        CaixaService service = new CaixaService(
                restTemplate,
                "https://servicebus2.caixa.gov.br/portaldeloterias/api/lotofacil"
        );

        server.expect(requestTo(
                        "https://servicebus2.caixa.gov.br/portaldeloterias/api/lotofacil/3210"
                ))
                .andRespond(withSuccess("""
                        {
                          "numero": 3210,
                          "dataApuracao": "18/05/2026",
                          "listaDezenas": ["01", "02", "03"]
                        }
                        """, MediaType.APPLICATION_JSON));

        CaixaResponseDTO response = service.obterConcurso(3210);

        assertThat(response.getNumero()).isEqualTo(3210);
        assertThat(response.getDataApuracao()).isEqualTo("18/05/2026");
        assertThat(response.getListaDezenas()).containsExactly("01", "02", "03");

        server.verify();
    }
}
