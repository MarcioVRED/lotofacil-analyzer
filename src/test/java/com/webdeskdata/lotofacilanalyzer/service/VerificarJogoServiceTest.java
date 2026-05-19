package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoRequest;
import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoResponse;
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
class VerificarJogoServiceTest {

    @Mock
    private ConcursoRepository concursoRepository;

    @InjectMocks
    private VerificarJogoService verificarJogoService;

    private ConcursoEntity concurso;

    @BeforeEach
    void setup() {

        concurso = criarConcurso(
                3200,
                LocalDate.of(2025, 5, 10),
                List.of(
                        1, 2, 3, 4, 5,
                        6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15
                )
        );
    }

    @Test
    @DisplayName("Deve retornar jogo encontrado")
    void deveRetornarJogoEncontrado() {

        when(concursoRepository.findAll())
                .thenReturn(List.of(concurso));

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15
                        )
                );

        VerificarJogoResponse response =
                verificarJogoService.verificar(request);

        assertNotNull(response);

        assertTrue(response.encontrado());

        assertEquals(3200, response.concurso());

        assertEquals(
                LocalDate.of(2025, 5, 10),
                response.data()
        );
    }

    @Test
    @DisplayName("Deve retornar jogo não encontrado")
    void deveRetornarJogoNaoEncontrado() {

        when(concursoRepository.findAll())
                .thenReturn(List.of(concurso));

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 25
                        )
                );

        VerificarJogoResponse response =
                verificarJogoService.verificar(request);

        assertNotNull(response);

        assertFalse(response.encontrado());

        assertNull(response.concurso());

        assertNull(response.data());
    }

    @Test
    @DisplayName("Deve retornar não encontrado quando não houver concursos")
    void deveRetornarNaoEncontradoQuandoNaoHouverConcursos() {

        when(concursoRepository.findAll())
                .thenReturn(List.of());

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15
                        )
                );

        VerificarJogoResponse response =
                verificarJogoService.verificar(request);

        assertNotNull(response);

        assertFalse(response.encontrado());

        assertNull(response.concurso());

        assertNull(response.data());
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