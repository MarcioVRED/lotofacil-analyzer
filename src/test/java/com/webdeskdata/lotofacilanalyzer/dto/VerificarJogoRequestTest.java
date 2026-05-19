package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificarJogoRequestTest {

    @Test
    @DisplayName("Deve criar VerificarJogoRequest corretamente")
    void deveCriarVerificarJogoRequest() {

        List<Integer> dezenas =
                List.of(
                        1, 2, 3, 4, 5,
                        6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15
                );

        VerificarJogoRequest request =
                new VerificarJogoRequest(dezenas);

        assertEquals(
                dezenas,
                request.dezenas()
        );
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        VerificarJogoRequest request1 =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15
                        )
                );

        VerificarJogoRequest request2 =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15
                        )
                );

        assertEquals(request1, request2);

        assertEquals(
                request1.hashCode(),
                request2.hashCode()
        );
    }

    @Test
    @DisplayName("Deve validar toString")
    void deveValidarToString() {

        VerificarJogoRequest request =
                new VerificarJogoRequest(
                        List.of(
                                1, 2, 3, 4, 5,
                                6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15
                        )
                );

        String texto = request.toString();

        assertTrue(texto.contains("1"));

        assertTrue(texto.contains("15"));

        assertTrue(texto.contains("dezenas"));
    }
}