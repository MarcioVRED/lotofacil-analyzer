package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseDTOTest {

    @Test
    @DisplayName("Deve criar resposta de sucesso corretamente")
    void deveCriarRespostaSucesso() {

        List<Integer> dados =
                List.of(1, 2, 3);

        ApiResponseDTO<List<Integer>> response =
                ApiResponseDTO.sucesso(
                        "Operação realizada com sucesso",
                        dados
                );

        assertTrue(response.sucesso());

        assertEquals(
                "Operação realizada com sucesso",
                response.mensagem()
        );

        assertEquals(
                dados,
                response.dados()
        );

        assertNull(response.erro());

        assertNotNull(response.timestamp());

        assertNull(response.path());
    }

    @Test
    @DisplayName("Deve criar resposta de erro corretamente")
    void deveCriarRespostaErro() {

        ApiErrorDTO erro =
                new ApiErrorDTO(
                        "NOT_FOUND",
                        404,
                        List.of("Recurso não encontrado")
                );

        ApiResponseDTO<Object> response =
                ApiResponseDTO.erro(
                        "Erro ao processar requisição",
                        erro,
                        "/api/teste"
                );

        assertFalse(response.sucesso());

        assertEquals(
                "Erro ao processar requisição",
                response.mensagem()
        );

        assertNull(response.dados());

        assertEquals(
                erro,
                response.erro()
        );

        assertNotNull(response.timestamp());

        assertEquals(
                "/api/teste",
                response.path()
        );
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        Instant agora = Instant.now();

        ApiResponseDTO<String> response1 =
                new ApiResponseDTO<>(
                        true,
                        "OK",
                        "dados",
                        null,
                        agora,
                        null
                );

        ApiResponseDTO<String> response2 =
                new ApiResponseDTO<>(
                        true,
                        "OK",
                        "dados",
                        null,
                        agora,
                        null
                );

        assertEquals(response1, response2);

        assertEquals(
                response1.hashCode(),
                response2.hashCode()
        );
    }

    @Test
    @DisplayName("Deve validar toString")
    void deveValidarToString() {

        ApiResponseDTO<String> response =
                new ApiResponseDTO<>(
                        true,
                        "Sucesso",
                        "dados",
                        null,
                        Instant.now(),
                        null
                );

        String texto = response.toString();

        assertTrue(texto.contains("Sucesso"));

        assertTrue(texto.contains("dados"));

        assertTrue(texto.contains("sucesso=true"));
    }
}