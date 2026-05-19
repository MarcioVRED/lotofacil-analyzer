package com.webdeskdata.lotofacilanalyzer.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CaixaResponseDTOTest {

    @Test
    void gettersSettersEqualsHashCodeEToStringFuncionam() {

        CaixaResponseDTO dto = new CaixaResponseDTO();

        dto.setNumero(3210);
        dto.setDataApuracao("18/05/2026");
        dto.setListaDezenas(List.of("01", "02", "03"));

        CaixaResponseDTO igual = new CaixaResponseDTO();

        igual.setNumero(3210);
        igual.setDataApuracao("18/05/2026");
        igual.setListaDezenas(List.of("01", "02", "03"));

        assertThat(dto.getNumero()).isEqualTo(3210);
        assertThat(dto.getDataApuracao()).isEqualTo("18/05/2026");
        assertThat(dto.getListaDezenas()).containsExactly("01", "02", "03");
        assertThat(dto).isEqualTo(igual).hasSameHashCodeAs(igual);
        assertThat(dto.toString()).contains("3210", "18/05/2026");
    }
}
