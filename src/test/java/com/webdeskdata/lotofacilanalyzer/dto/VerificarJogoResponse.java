package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VerificarJogoResponseTest {

    @Test
    @DisplayName("Deve criar VerificarJogoResponse quando jogo encontrado")
    void deveCriarResponseEncontrado() {

        VerificarJogoResponse response =
                new VerificarJogoResponse(
                        true,
                        1234,
                        LocalDate.of(2025, 5, 10)
                );

        assertTrue(response.encontrado());
        assertEquals(1234, response.concurso());
        assertEquals(LocalDate.of(2025, 5, 10), response.data());
    }

    @Test
    @DisplayName("Deve criar VerificarJogoResponse quando jogo não encontrado")
    void deveCriarResponseNaoEncontrado() {

        VerificarJogoResponse response =
                new VerificarJogoResponse(
                        false,
                        null,
                        null
                );

        assertFalse(response.encontrado());
        assertNull(response.concurso());
        assertNull(response.data());
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        VerificarJogoResponse r1 =
                new VerificarJogoResponse(
                        true,
                        1000,
                        LocalDate.of(2025, 1, 1)
                );

        VerificarJogoResponse r2 =
                new VerificarJogoResponse(
                        true,
                        1000,
                        LocalDate.of(2025, 1, 1)
                );

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Deve validar toString")
    void deveValidarToString() {

        VerificarJogoResponse response =
                new VerificarJogoResponse(
                        true,
                        999,
                        LocalDate.of(2025, 12, 31)
                );

        String text = response.toString();

        assertTrue(text.contains("999"));
        assertTrue(text.contains("true"));
    }
}