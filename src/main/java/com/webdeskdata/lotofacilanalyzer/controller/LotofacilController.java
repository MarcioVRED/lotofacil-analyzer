package com.webdeskdata.lotofacilanalyzer.controller;

import com.webdeskdata.lotofacilanalyzer.dto.ApiResponseDTO;
import com.webdeskdata.lotofacilanalyzer.dto.SyncResultadoDTO;
import com.webdeskdata.lotofacilanalyzer.service.CsvService;
import com.webdeskdata.lotofacilanalyzer.service.ExcelService;
import com.webdeskdata.lotofacilanalyzer.service.EstatisticaService;
import com.webdeskdata.lotofacilanalyzer.service.SyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/lotofacil")
@RequiredArgsConstructor
public class LotofacilController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LotofacilController.class);

    private final SyncService syncService;
    private final CsvService csvService;
    private final ExcelService excelService;
    private final EstatisticaService estatisticaService;

    @PostMapping("/sync")
    public ResponseEntity<ApiResponseDTO<SyncResultadoDTO>> sincronizar() {

        LOGGER.info("Requisição recebida para sincronizar concursos");

        SyncResultadoDTO resultado = syncService.sincronizar();

        return ResponseEntity.ok(
                ApiResponseDTO.sucesso(
                        "Sincronização concluída com sucesso.",
                        resultado
                )
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponseDTO<Map<Integer, Integer>>> estatisticas() {

        LOGGER.info("Requisição recebida para consultar frequência das dezenas");

        return ResponseEntity.ok(
                ApiResponseDTO.sucesso(
                        "Estatísticas consultadas com sucesso.",
                        estatisticaService.frequencia()
                )
        );
    }

    @Operation(
            summary = "Exporta o dataset em CSV",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Arquivo CSV",
                    content = @Content(
                            mediaType = "text/csv",
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
    )
    @GetMapping(
            value = "/export/csv",
            produces = "text/csv"
    )
    public ResponseEntity<Resource> exportarCsv() throws Exception {

        LOGGER.info("Requisição recebida para exportar CSV");

        Path arquivo = Path.of(csvService.exportar())
                .toAbsolutePath()
                .normalize();
        Resource resource = new FileSystemResource(arquivo);

        return ResponseEntity.status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"lotofacil_ai_ready.csv\""
                )
                .contentLength(resource.contentLength())
                .contentType(
                        MediaType.parseMediaType("text/csv")
                )
                .body(resource);
    }

    @Operation(
            summary = "Exporta o dataset em Excel",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Arquivo Excel",
                    content = @Content(
                            mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
    )
    @GetMapping(
            value = "/export/excel",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
    public ResponseEntity<Resource> exportarExcel() throws Exception {

        LOGGER.info("Requisição recebida para exportar Excel");

        Path arquivo = Path.of(excelService.exportar())
                .toAbsolutePath()
                .normalize();
        Resource resource = new FileSystemResource(arquivo);

        return ResponseEntity.status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"lotofacil_ai_ready.xlsx\""
                )
                .contentLength(resource.contentLength())
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(resource);
    }
}
