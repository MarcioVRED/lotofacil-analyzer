package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnaliseRepeticaoResponseDTOTest {

    @Test
    @DisplayName("Deve criar AnaliseRepeticaoResponse corretamente")
    void deveCriarAnaliseRepeticaoResponse() {

        LocalDate data1 = LocalDate.of(2025, 1, 10);
        LocalDate data2 = LocalDate.of(2025, 1, 11);

        List<Integer> coincidentes =
                List.of(1,2,3,4,5,6,7,8,9,10,11,12);

        List<Integer> diferentes1 =
                List.of(13,14,15);

        List<Integer> diferentes2 =
                List.of(20,21,22);

        AnaliseRepeticaoResponse response =
                new AnaliseRepeticaoResponse(
                        1000,
                        1001,
                        data1,
                        data2,
                        12,
                        coincidentes,
                        diferentes1,
                        diferentes2
                );

        assertEquals(1000, response.concurso1());
        assertEquals(1001, response.concurso2());

        assertEquals(data1, response.data1());
        assertEquals(data2, response.data2());

        assertEquals(12, response.dezenasIguais());

        assertEquals(coincidentes, response.dezenasCoincidentes());

        assertEquals(diferentes1, response.diferentesConcurso1());

        assertEquals(diferentes2, response.diferentesConcurso2());
    }

    @Test
    @DisplayName("Deve validar equals e hashCode")
    void deveValidarEqualsEHashCode() {

        AnaliseRepeticaoResponse response1 =
                new AnaliseRepeticaoResponse(
                        1000,
                        1001,
                        LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 11),
                        12,
                        List.of(1,2,3),
                        List.of(4,5),
                        List.of(6,7)
                );

        AnaliseRepeticaoResponse response2 =
                new AnaliseRepeticaoResponse(
                        1000,
                        1001,
                        LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 11),
                        12,
                        List.of(1,2,3),
                        List.of(4,5),
                        List.of(6,7)
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

        AnaliseRepeticaoResponse response =
                new AnaliseRepeticaoResponse(
                        1000,
                        1001,
                        LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 11),
                        12,
                        List.of(1,2,3),
                        List.of(4,5),
                        List.of(6,7)
                );

        String texto = response.toString();

        assertTrue(texto.contains("1000"));
        assertTrue(texto.contains("1001"));
        assertTrue(texto.contains("dezenasIguais=12"));
    }
}
