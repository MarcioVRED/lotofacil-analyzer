package com.webdeskdata.lotofacilanalyzer.controller;

import com.webdeskdata.lotofacilanalyzer.dto.AnaliseRepeticaoResponse;
import com.webdeskdata.lotofacilanalyzer.service.AnaliseRepeticaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnaliseRepeticaoController.class)
class AnaliseRepeticaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnaliseRepeticaoService analiseRepeticaoService;

    @Test
    @DisplayName("Deve retornar concursos parecidos com default de 14 dezenas iguais")
    void deveRetornarConcursosParecidos() throws Exception {

        AnaliseRepeticaoResponse response =
                new AnaliseRepeticaoResponse(
                        1000,
                        1001,
                        LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 11),
                        14,
                        List.of(1,2,3,4),
                        List.of(5,6),
                        List.of(7,8)
                );

        when(analiseRepeticaoService.buscarConcursosParecidos(14))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/analise/repeticao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].concurso1").value(1000))
                .andExpect(jsonPath("$[0].concurso2").value(1001))
                .andExpect(jsonPath("$[0].dezenasIguais").value(14));
    }

    @Test
    @DisplayName("Deve aceitar parametro 'iguais' via request param")
    void deveAceitarParametroIguais() throws Exception {

        when(analiseRepeticaoService.buscarConcursosParecidos(12))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/analise/repeticao")
                        .param("iguais", "12"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver resultados")
    void deveRetornarListaVazia() throws Exception {

        when(analiseRepeticaoService.buscarConcursosParecidos(14))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/analise/repeticao"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}