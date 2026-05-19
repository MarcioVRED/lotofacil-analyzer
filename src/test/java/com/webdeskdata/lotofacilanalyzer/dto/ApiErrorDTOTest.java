package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorDTOTest {

    @Test
    @DisplayName("Deve criar ApiErrorDTO corretamente")
    void deveCriarApiErrorDTO() {

        List<String> detalhes = List.of(
                "Campo nome é obrigatório",
                "Campo email inválido"
        );

        ApiErrorDTO dto =
                new ApiErrorDTO(
                        "VALIDATION_ERROR",
                        400,
                        detalhes
                );

        assertEquals(
                "VALIDATION_ERROR",
                dto.codigo()
        );

        assertEquals(
                400,
                dto.status()
        );

        assertEquals(
                detalhes,
                dto.detalhes()
        );
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        ApiErrorDTO dto1 =
                new ApiErrorDTO(
                        "NOT_FOUND",
                        404,
                        List.of("Recurso não encontrado")
                );

        ApiErrorDTO dto2 =
                new ApiErrorDTO(
                        "NOT_FOUND",
                        404,
                        List.of("Recurso não encontrado")
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

        ApiErrorDTO dto =
                new ApiErrorDTO(
                        "INTERNAL_ERROR",
                        500,
                        List.of("Erro interno")
                );

        String texto = dto.toString();

        assertTrue(
                texto.contains("INTERNAL_ERROR")
        );

        assertTrue(
                texto.contains("500")
        );

        assertTrue(
                texto.contains("Erro interno")
        );
    }
}