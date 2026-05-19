package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CsvService.class);

    private final ConcursoRepository repository;
    private final Path outputDirectory;

    @Autowired
    public CsvService(
            ConcursoRepository repository,
            @Value("${lotofacil.export.directory:.}") String outputDirectory
    ) {
        this(repository, Path.of(outputDirectory));
    }

    CsvService(
            ConcursoRepository repository,
            Path outputDirectory
    ) {
        this.repository = repository;
        this.outputDirectory = outputDirectory;
    }

    public String exportar() throws Exception {

        List<ConcursoEntity> concursos = repository.findAll();

        LOGGER.info("Exportando {} concursos para CSV", concursos.size());

        Files.createDirectories(outputDirectory);

        Path arquivo = outputDirectory.resolve("lotofacil_ai_ready.csv");

        try (CSVWriter writer =
                     new CSVWriter(new FileWriter(arquivo.toFile()))) {

            writer.writeNext(DatasetExportMapper.HEADERS);

            for (ConcursoEntity c : concursos) {
                writer.writeNext(toStringArray(DatasetExportMapper.values(c)));
            }
        }

        LOGGER.info("Arquivo CSV gerado em {}", arquivo.toAbsolutePath().normalize());

        return arquivo.toString();
    }

    private String[] toStringArray(Object[] values) {

        String[] strings = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].toString();
        }

        return strings;
    }
}
