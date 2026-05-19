package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.AnaliseRepeticaoResponse;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnaliseRepeticaoServiceTest {

    @Mock
    private ConcursoRepository concursoRepository;

    @InjectMocks
    private AnaliseRepeticaoService analiseRepeticaoService;

    private ConcursoEntity concurso1;
    private ConcursoEntity concurso2;
    private ConcursoEntity concurso3;

    @BeforeEach
    void setup() {

        concurso1 = criarConcurso(
                1000,
                LocalDate.of(2025, 1, 1),
                List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
        );

        concurso2 = criarConcurso(
                1001,
                LocalDate.of(2025, 1, 2),
                List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,20,21)
        );

        concurso3 = criarConcurso(
                1002,
                LocalDate.of(2025, 1, 3),
                List.of(16,17,18,19,20,21,22,23,24,25,1,2,3,4,5)
        );
    }

    @Test
    @DisplayName("Deve retornar concursos com pelo menos 12 dezenas iguais")
    void deveRetornarConcursosParecidos() {

        when(concursoRepository.findAll())
                .thenReturn(List.of(concurso1, concurso2, concurso3));

        List<AnaliseRepeticaoResponse> resultado =
                analiseRepeticaoService.buscarConcursosParecidos(12);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        AnaliseRepeticaoResponse response = resultado.getFirst();

        assertEquals(1000, response.concurso1());
        assertEquals(1001, response.concurso2());

        assertEquals(13, response.dezenasIguais());

        assertEquals(
                List.of(1,2,3,4,5,6,7,8,9,10,11,12,13),
                response.dezenasCoincidentes()
        );

        assertEquals(
                List.of(14,15),
                response.diferentesConcurso1()
        );

        assertEquals(
                List.of(20,21),
                response.diferentesConcurso2()
        );
    }

    @Test
    @DisplayName("Não deve retornar concursos quando não houver mínimo de dezenas iguais")
    void naoDeveRetornarQuandoNaoHouverMinimo() {

        when(concursoRepository.findAll())
                .thenReturn(List.of(concurso1, concurso3));

        List<AnaliseRepeticaoResponse> resultado =
                analiseRepeticaoService.buscarConcursosParecidos(12);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver concursos")
    void deveRetornarListaVazia() {

        when(concursoRepository.findAll())
                .thenReturn(List.of());

        List<AnaliseRepeticaoResponse> resultado =
                analiseRepeticaoService.buscarConcursosParecidos(12);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    private ConcursoEntity criarConcurso(
            Integer numero,
            LocalDate data,
            List<Integer> dezenas
    ) {

        ConcursoEntity c = new ConcursoEntity();

        c.setConcurso(numero);
        c.setData(data);

        c.setD01(dezenas.get(0));
        c.setD02(dezenas.get(1));
        c.setD03(dezenas.get(2));
        c.setD04(dezenas.get(3));
        c.setD05(dezenas.get(4));
        c.setD06(dezenas.get(5));
        c.setD07(dezenas.get(6));
        c.setD08(dezenas.get(7));
        c.setD09(dezenas.get(8));
        c.setD10(dezenas.get(9));
        c.setD11(dezenas.get(10));
        c.setD12(dezenas.get(11));
        c.setD13(dezenas.get(12));
        c.setD14(dezenas.get(13));
        c.setD15(dezenas.get(14));

        return c;
    }
}