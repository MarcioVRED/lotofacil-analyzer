package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EstatisticaServiceTest {

    private final ConcursoRepository repository = mock(ConcursoRepository.class);
    private final EstatisticaService service = new EstatisticaService(repository);

    @Test
    void frequenciaContaTodasAsDezenasDosConcursos() {

        when(repository.findAll()).thenReturn(List.of(
                concurso(1, 120, 7),
                concurso(2, 150, 8)
        ));

        Map<Integer, Integer> frequencia = service.frequencia();

        assertThat(frequencia).hasSize(25);
        assertThat(frequencia.get(1)).isEqualTo(2);
        assertThat(frequencia.get(15)).isEqualTo(2);
        assertThat(frequencia.get(25)).isZero();
    }

    @Test
    void resumoCalculaMediasETotal() {

        when(repository.findAll()).thenReturn(List.of(
                concurso(1, 120, 7),
                concurso(2, 150, 8)
        ));

        Map<String, Object> resumo = service.resumo();

        assertThat(resumo)
                .containsEntry("totalConcursos", 2)
                .containsEntry("mediaSoma", 135.0)
                .containsEntry("mediaPares", 7.5)
                .containsEntry("mediaImpares", 7.5);
        assertThat(resumo.get("frequencia")).isInstanceOf(Map.class);
    }

    private ConcursoEntity concurso(
            int numero,
            int soma,
            int pares
    ) {

        return ConcursoEntity.builder()
                .concurso(numero)
                .data(LocalDate.of(2026, 5, numero))
                .d01(1)
                .d02(2)
                .d03(3)
                .d04(4)
                .d05(5)
                .d06(6)
                .d07(7)
                .d08(8)
                .d09(9)
                .d10(10)
                .d11(11)
                .d12(12)
                .d13(13)
                .d14(14)
                .d15(15)
                .soma(soma)
                .pares(pares)
                .impares(15 - pares)
                .build();
    }
}
