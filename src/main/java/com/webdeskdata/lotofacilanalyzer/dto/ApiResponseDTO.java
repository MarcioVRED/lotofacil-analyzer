package com.webdeskdata.lotofacilanalyzer.dto;

import java.time.Instant;

public record ApiResponseDTO<T>(
        boolean sucesso,
        String mensagem,
        T dados,
        ApiErrorDTO erro,
        Instant timestamp,
        String path
) {

    public static <T> ApiResponseDTO<T> sucesso(
            String mensagem,
            T dados
    ) {
        return new ApiResponseDTO<>(
                true,
                mensagem,
                dados,
                null,
                Instant.now(),
                null
        );
    }

    public static <T> ApiResponseDTO<T> erro(
            String mensagem,
            ApiErrorDTO erro,
            String path
    ) {
        return new ApiResponseDTO<>(
                false,
                mensagem,
                null,
                erro,
                Instant.now(),
                path
        );
    }
}
