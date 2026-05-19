package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ExcelService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ExcelService.class);

    private final ConcursoRepository repository;
    private final Path outputDirectory;

    @Autowired
    public ExcelService(
            ConcursoRepository repository,
            @Value("${lotofacil.export.directory:.}") String outputDirectory
    ) {
        this(repository, Path.of(outputDirectory));
    }

    ExcelService(
            ConcursoRepository repository,
            Path outputDirectory
    ) {
        this.repository = repository;
        this.outputDirectory = outputDirectory;
    }

    public String exportar() throws Exception {

        List<ConcursoEntity> concursos = repository.findAll();

        LOGGER.info("Exportando {} concursos para Excel", concursos.size());

        Files.createDirectories(outputDirectory);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Lotofacil");

            Row header = sheet.createRow(0);

            for (int i = 0; i < DatasetExportMapper.HEADERS.length; i++) {
                header.createCell(i).setCellValue(DatasetExportMapper.HEADERS[i]);
            }

            int rowNum = 1;

            for (ConcursoEntity c : concursos) {

                Row row = sheet.createRow(rowNum++);

                Object[] values = DatasetExportMapper.values(c);

                for (int i = 0; i < values.length; i++) {
                    if (values[i] instanceof Number number) {
                        row.createCell(i).setCellValue(number.doubleValue());
                    } else {
                        row.createCell(i).setCellValue(values[i].toString());
                    }
                }
            }

            for (int i = 0; i < DatasetExportMapper.HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            Path arquivo = outputDirectory.resolve("lotofacil_ai_ready.xlsx");

            try (FileOutputStream out = new FileOutputStream(arquivo.toFile())) {
                workbook.write(out);
            }

            LOGGER.info("Arquivo Excel gerado em {}", arquivo.toAbsolutePath().normalize());

            return arquivo.toString();
        }
    }
}
