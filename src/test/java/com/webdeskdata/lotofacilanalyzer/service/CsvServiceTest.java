package com.webdeskdata.lotofacilanalyzer.service;

import com.opencsv.CSVReader;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvServiceTest {

    @TempDir
    Path tempDir;

    private final ConcursoRepository repository = mock(ConcursoRepository.class);

    @Test
    void exportarGeraArquivoCsvComCabecalhoELinhas() throws Exception {

        when(repository.findAll()).thenReturn(List.of(concurso()));

        CsvService service = new CsvService(repository, tempDir);

        Path arquivo = Path.of(service.exportar());

        assertThat(arquivo).exists().isRegularFile();
        assertThat(arquivo.getParent()).isEqualTo(tempDir);
        assertThat(Files.size(arquivo)).isPositive();

        try (CSVReader reader = new CSVReader(new FileReader(arquivo.toFile()))) {
            List<String[]> linhas = reader.readAll();

            assertThat(linhas).hasSize(2);
            assertThat(linhas.getFirst())
                    .containsExactly(
                            "concurso", "data",
                            "d01", "d02", "d03", "d04", "d05",
                            "d06", "d07", "d08", "d09", "d10",
                            "d11", "d12", "d13", "d14", "d15",
                            "soma", "pares", "impares", "primos",
                            "fibonacci", "moldura", "miolo"
                    );
            assertThat(linhas.get(1))
                    .containsExactly(
                            "3210", "2026-05-18",
                            "1", "2", "3", "4", "5",
                            "6", "7", "8", "9", "10",
                            "11", "12", "13", "14", "15",
                            "120", "7", "8", "6", "5", "10", "5"
                    );
        }
    }

    private ConcursoEntity concurso() {

        return ConcursoEntity.builder()
                .concurso(3210)
                .data(LocalDate.of(2026, 5, 18))
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
                .soma(120)
                .pares(7)
                .impares(8)
                .primos(6)
                .fibonacci(5)
                .moldura(10)
                .miolo(5)
                .build();
    }
}
