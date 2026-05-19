package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExcelServiceTest {

    @TempDir
    Path tempDir;

    private final ConcursoRepository repository = mock(ConcursoRepository.class);

    @Test
    void exportarGeraArquivoExcelComCabecalhoELinhas() throws Exception {

        when(repository.findAll()).thenReturn(List.of(concurso()));

        ExcelService service = new ExcelService(repository, tempDir);

        Path arquivo = Path.of(service.exportar());

        assertThat(arquivo).exists().isRegularFile();
        assertThat(arquivo.getParent()).isEqualTo(tempDir);
        assertThat(Files.size(arquivo)).isPositive();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(arquivo.toFile()))) {
            var sheet = workbook.getSheet("Lotofacil");

            assertThat(sheet).isNotNull();
            assertThat(sheet.getRow(0).getCell(0).getStringCellValue()).isEqualTo("concurso");
            assertThat(sheet.getRow(0).getCell(23).getStringCellValue()).isEqualTo("miolo");
            assertThat(sheet.getRow(1).getCell(0).getNumericCellValue()).isEqualTo(3210.0);
            assertThat(sheet.getRow(1).getCell(1).getStringCellValue()).isEqualTo("2026-05-18");
            assertThat(sheet.getRow(1).getCell(17).getNumericCellValue()).isEqualTo(120.0);
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
