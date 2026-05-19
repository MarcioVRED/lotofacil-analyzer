package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConcursoMapperTest {

    private final ConcursoMapper mapper = new ConcursoMapper();

    @Test
    void toEntityOrdenaDezenasECalculaMetricas() {

        ConcursoEntity entity = mapper.toEntity(dto(List.of(
                "15", "14", "13", "12", "11",
                "10", "09", "08", "07", "06",
                "05", "04", "03", "02", "01"
        )));

        assertThat(entity.getConcurso()).isEqualTo(7);
        assertThat(entity.getData()).isEqualTo(LocalDate.of(2026, 5, 18));
        assertThat(entity.getDezenas())
                .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        assertThat(entity.getSoma()).isEqualTo(120);
        assertThat(entity.getPares()).isEqualTo(7);
        assertThat(entity.getImpares()).isEqualTo(8);
        assertThat(entity.getPrimos()).isEqualTo(6);
        assertThat(entity.getFibonacci()).isEqualTo(6);
        assertThat(entity.getMoldura()).isEqualTo(9);
        assertThat(entity.getMiolo()).isEqualTo(6);
    }

    @Test
    void toEntityRejeitaQuantidadeInvalidaDeDezenas() {

        assertThatThrownBy(() -> mapper.toEntity(dto(List.of("01", "02"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("possui 2 dezenas");
    }

    private CaixaResponseDTO dto(List<String> dezenas) {

        CaixaResponseDTO dto = new CaixaResponseDTO();

        dto.setNumero(7);
        dto.setDataApuracao("18/05/2026");
        dto.setListaDezenas(dezenas);

        return dto;
    }
}
