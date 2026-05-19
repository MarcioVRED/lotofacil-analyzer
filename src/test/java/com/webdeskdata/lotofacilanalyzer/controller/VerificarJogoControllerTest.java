package com.webdeskdata.lotofacilanalyzer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoRequest;
import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoResponse;
import com.webdeskdata.lotofacilanalyzer.service.VerificarJogoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerificarJogoController.class)
class VerificarJogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VerificarJogoService verificarJogoService;

    @Test
    @DisplayName("Deve retornar jogo encontrado com sucesso")
    void deveRetornarJogoEncontrado() throws Exception {

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1,2,3,4,5,
                                6,7,8,9,10,
                                11,12,13,14,15
                        )
                );

        VerificarJogoResponse response =
                new VerificarJogoResponse(
                        true,
                        1234,
                        LocalDate.of(2025, 5, 10)
                );

        when(verificarJogoService.verificar(request))
                .thenReturn(response);

        mockMvc.perform(post("/api/concursos/verificar-jogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.encontrado").value(true))
                .andExpect(jsonPath("$.concurso").value(1234))
                .andExpect(jsonPath("$.data").value("2025-05-10"));
    }

    @Test
    @DisplayName("Deve retornar jogo não encontrado")
    void deveRetornarJogoNaoEncontrado() throws Exception {

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1,2,3,4,5,
                                6,7,8,9,10,
                                11,12,13,14,15
                        )
                );

        VerificarJogoResponse response =
                new VerificarJogoResponse(
                        false,
                        null,
                        null
                );

        when(verificarJogoService.verificar(request))
                .thenReturn(response);

        mockMvc.perform(post("/api/concursos/verificar-jogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.encontrado").value(false))
                .andExpect(jsonPath("$.concurso").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("Deve validar JSON de entrada")
    void deveValidarJsonEntrada() throws Exception {

        String json =
                """
                {
                    "dezenas": [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
                }
                """;

        when(verificarJogoService.verificar(org.mockito.ArgumentMatchers.any()))
                .thenReturn(new VerificarJogoResponse(false, null, null));

        mockMvc.perform(post("/api/concursos/verificar-jogo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}