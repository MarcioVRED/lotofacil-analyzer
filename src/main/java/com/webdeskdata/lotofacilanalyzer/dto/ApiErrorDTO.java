package com.webdeskdata.lotofacilanalyzer.dto;

import java.util.List;

public record ApiErrorDTO(
        String codigo,
        int status,
        List<String> detalhes
) {
}
