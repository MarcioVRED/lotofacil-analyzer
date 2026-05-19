package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SyncResultadoDTOTest {

    @Test
    @DisplayName("Deve criar SyncResultadoDTO corretamente")
    void deveCriarSyncResultadoDTO() {

        SyncResultadoDTO dto =
                new SyncResultadoDTO(
                        100,
                        80,
                        15,
                        5
                );

        assertEquals(
                100,
                dto.totalVerificados()
        );

        assertEquals(
                80,
                dto.salvos()
        );

        assertEquals(
                15,
                dto.ignorados()
        );

        assertEquals(
                5,
                dto.erros()
        );
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        SyncResultadoDTO dto1 =
                new SyncResultadoDTO(
                        100,
                        80,
                        15,
                        5
                );

        SyncResultadoDTO dto2 =
                new SyncResultadoDTO(
                        100,
                        80,
                        15,
                        5
                );

        assertEquals(dto1, dto2);

        assertEquals(
                dto1.hashCode(),
                dto2.hashCode()
        );
    }

    @Test
    @DisplayName("Deve validar toString")
    void deveValidarToString() {

        SyncResultadoDTO dto =
                new SyncResultadoDTO(
                        100,
                        80,
                        15,
                        5
                );

        String texto = dto.toString();

        assertTrue(texto.contains("100"));

        assertTrue(texto.contains("80"));

        assertTrue(texto.contains("15"));

        assertTrue(texto.contains("5"));
    }
}