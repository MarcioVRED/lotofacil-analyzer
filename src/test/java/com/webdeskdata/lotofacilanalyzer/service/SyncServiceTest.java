package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import com.webdeskdata.lotofacilanalyzer.dto.SyncResultadoDTO;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SyncServiceTest {

    private final CaixaService caixaService = mock(CaixaService.class);
    private final ConcursoRepository repository = mock(ConcursoRepository.class);
    private final ConcursoMapper concursoMapper = new ConcursoMapper();
    private final SyncService service = new SyncService(
            caixaService,
            repository,
            concursoMapper,
            10
    );

    @Test
    void sincronizarBuscaConcursoInexistenteCalculaMetricasESalva() {

        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsById(7)).thenReturn(false);
        when(caixaService.obterConcurso(7)).thenReturn(dto());

        SyncResultadoDTO resultado = service.sincronizar();

        ArgumentCaptor<ConcursoEntity> captor =
                ArgumentCaptor.forClass(ConcursoEntity.class);

        verify(repository).save(captor.capture());

        ConcursoEntity salvo = captor.getValue();

        assertThat(salvo.getConcurso()).isEqualTo(7);
        assertThat(salvo.getData()).isEqualTo(LocalDate.of(2026, 5, 18));
        assertThat(List.of(
                salvo.getD01(),
                salvo.getD02(),
                salvo.getD03(),
                salvo.getD04(),
                salvo.getD05()
        )).containsExactly(1, 2, 3, 4, 5);
        assertThat(salvo.getSoma()).isEqualTo(120);
        assertThat(salvo.getPares()).isEqualTo(7);
        assertThat(salvo.getImpares()).isEqualTo(8);
        assertThat(salvo.getPrimos()).isEqualTo(6);
        assertThat(salvo.getFibonacci()).isEqualTo(6);
        assertThat(salvo.getMoldura()).isEqualTo(9);
        assertThat(salvo.getMiolo()).isEqualTo(6);
        assertThat(resultado).isEqualTo(new SyncResultadoDTO(10, 1, 9, 0));
    }

    @Test
    void sincronizarNaoSalvaQuandoCaixaRetornaNulo() {

        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsById(9)).thenReturn(false);
        when(caixaService.obterConcurso(9)).thenReturn(null);

        SyncResultadoDTO resultado = service.sincronizar();

        verify(repository, never()).save(any());
        assertThat(resultado).isEqualTo(new SyncResultadoDTO(10, 0, 10, 0));
    }

    private CaixaResponseDTO dto() {

        CaixaResponseDTO dto = new CaixaResponseDTO();

        dto.setNumero(7);
        dto.setDataApuracao("18/05/2026");
        dto.setListaDezenas(List.of(
                "15", "14", "13", "12", "11",
                "10", "09", "08", "07", "06",
                "05", "04", "03", "02", "01"
        ));

        return dto;
    }
}
