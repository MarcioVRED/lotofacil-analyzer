package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import com.webdeskdata.lotofacilanalyzer.dto.SyncResultadoDTO;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SyncService.class);

    private final CaixaService caixaService;

    private final ConcursoRepository repository;

    private final ConcursoMapper concursoMapper;

    private final int maxConcurso;

    @Autowired
    public SyncService(
            CaixaService caixaService,
            ConcursoRepository repository,
            ConcursoMapper concursoMapper,
            @Value("${lotofacil.sync.max-concurso:4000}") int maxConcurso
    ) {
        this.caixaService = caixaService;
        this.repository = repository;
        this.concursoMapper = concursoMapper;
        this.maxConcurso = maxConcurso;
    }

    SyncService(
            CaixaService caixaService,
            ConcursoRepository repository
    ) {
        this(caixaService, repository, new ConcursoMapper(), 4000);
    }

    public SyncResultadoDTO sincronizar() {

        LOGGER.info("Iniciando sincronização de concursos até {}", maxConcurso);

        int salvos = 0;
        int ignorados = 0;
        int erros = 0;

        for (int i = 1; i <= maxConcurso; i++) {

            try {
                if (sincronizarConcurso(i)) {
                    salvos++;
                } else {
                    ignorados++;
                }
            } catch (Exception ex) {
                erros++;
                LOGGER.warn("Erro ao sincronizar concurso {}", i, ex);
            }
        }

        SyncResultadoDTO resultado = new SyncResultadoDTO(
                maxConcurso,
                salvos,
                ignorados,
                erros
        );

        LOGGER.info(
                "Sincronização concluída. verificados={}, salvos={}, ignorados={}, erros={}",
                resultado.totalVerificados(),
                resultado.salvos(),
                resultado.ignorados(),
                resultado.erros()
        );

        return resultado;
    }

    private boolean sincronizarConcurso(int numero) {

        if (repository.existsById(numero)) {
            return false;
        }

        CaixaResponseDTO dto = caixaService.obterConcurso(numero);

        if (dto == null) {
            return false;
        }

        ConcursoEntity entity = concursoMapper.toEntity(dto);

        repository.save(entity);

        LOGGER.info("Concurso salvo: {}", numero);

        return true;
    }
}
