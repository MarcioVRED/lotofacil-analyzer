package com.webdeskdata.lotofacilanalyzer.dto;

import java.time.LocalDate;
import java.util.List;

public record AnaliseRepeticaoResponse(
        Integer concurso1,
        Integer concurso2,
        LocalDate data1,
        LocalDate data2,
        Integer dezenasIguais,
        List<Integer> dezenasCoincidentes,
        List<Integer> diferentesConcurso1,
        List<Integer> diferentesConcurso2
) {
}