package com.webdeskdata.lotofacilanalyzer.controller;

import com.webdeskdata.lotofacilanalyzer.dto.ApiResponseDTO;
import com.webdeskdata.lotofacilanalyzer.dto.SyncResultadoDTO;
import com.webdeskdata.lotofacilanalyzer.service.CsvService;
import com.webdeskdata.lotofacilanalyzer.service.ExcelService;
import com.webdeskdata.lotofacilanalyzer.service.EstatisticaService;
import com.webdeskdata.lotofacilanalyzer.service.SyncService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LotofacilControllerTest {

    @TempDir
    Path tempDir;

    private final SyncService syncService = mock(SyncService.class);
    private final CsvService csvService = mock(CsvService.class);
    private final ExcelService excelService = mock(ExcelService.class);
    private final EstatisticaService estatisticaService = mock(EstatisticaService.class);

    private final LotofacilController controller = new LotofacilController(
            syncService,
            csvService,
            excelService,
            estatisticaService
    );

    @Test
    void sincronizarChamaServiceERetornaMensagem() {

        SyncResultadoDTO resultado = new SyncResultadoDTO(10, 2, 7, 1);

        when(syncService.sincronizar()).thenReturn(resultado);

        ResponseEntity<ApiResponseDTO<SyncResultadoDTO>> response = controller.sincronizar();

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sucesso()).isTrue();
        assertThat(response.getBody().mensagem()).isEqualTo("Sincronização concluída com sucesso.");
        assertThat(response.getBody().dados()).isEqualTo(resultado);
        verify(syncService).sincronizar();
    }

    @Test
    void estatisticasRetornaFrequenciaDoService() {

        Map<Integer, Integer> frequencia = Map.of(1, 10, 2, 5);

        when(estatisticaService.frequencia()).thenReturn(frequencia);

        ResponseEntity<ApiResponseDTO<Map<Integer, Integer>>> response = controller.estatisticas();

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().sucesso()).isTrue();
        assertThat(response.getBody().mensagem()).isEqualTo("Estatísticas consultadas com sucesso.");
        assertThat(response.getBody().dados()).isEqualTo(frequencia);
    }

    @Test
    void exportarCsvRetornaResourceComHeadersDeDownload() throws Exception {

        Path arquivo = tempDir.resolve("lotofacil_ai_ready.csv");
        Files.writeString(arquivo, "concurso,data\n1,2026-05-18\n");

        when(csvService.exportar()).thenReturn(arquivo.toString());

        ResponseEntity<Resource> response = controller.exportarCsv();

        assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("attachment; filename=\"lotofacil_ai_ready.csv\"");
        assertThat(response.getHeaders().getContentType())
                .isEqualTo(MediaType.parseMediaType("text/csv"));
        assertThat(response.getHeaders().getContentLength()).isEqualTo(Files.size(arquivo));
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().exists()).isTrue();
    }

    @Test
    void exportarExcelRetornaResourceComHeadersDeDownload() throws Exception {

        Path arquivo = tempDir.resolve("lotofacil_ai_ready.xlsx");
        Files.write(arquivo, new byte[]{1, 2, 3});

        when(excelService.exportar()).thenReturn(arquivo.toString());

        ResponseEntity<Resource> response = controller.exportarExcel();

        assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("attachment; filename=\"lotofacil_ai_ready.xlsx\"");
        assertThat(response.getHeaders().getContentType())
                .isEqualTo(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ));
        assertThat(response.getHeaders().getContentLength()).isEqualTo(Files.size(arquivo));
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().exists()).isTrue();
    }
}
