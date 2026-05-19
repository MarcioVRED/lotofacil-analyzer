package com.webdeskdata.lotofacilanalyzer.dto;

import lombok.Data;

import java.util.List;

@Data
public class CaixaResponseDTO {

    private Integer numero;

    private String dataApuracao;

    private List<String> listaDezenas;
}