package com.webdeskdata.lotofacilanalyzer.dto;

public record SyncResultadoDTO(
        int totalVerificados,
        int salvos,
        int ignorados,
        int erros
) {
}
