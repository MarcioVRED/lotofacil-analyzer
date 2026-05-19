package com.webdeskdata.lotofacilanalyzer.dto;


import java.time.LocalDate;

public record VerificarJogoResponse(
        Boolean encontrado,
        Integer concurso,
        LocalDate data
) {
}