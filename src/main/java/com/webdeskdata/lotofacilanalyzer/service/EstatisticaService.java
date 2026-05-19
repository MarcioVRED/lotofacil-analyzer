package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EstatisticaService.class);

    private final ConcursoRepository repository;

    public Map<Integer, Integer> frequencia() {

        List<ConcursoEntity> concursos =
                repository.findAll();

        LOGGER.debug("Calculando frequência para {} concursos", concursos.size());

        return frequencia(concursos);
    }

    private Map<Integer, Integer> frequencia(List<ConcursoEntity> concursos) {

        Map<Integer, Integer> mapa =
                inicializarMapa();

        concursos.forEach(concurso -> {
            concurso.getDezenas().forEach(dezena -> incrementar(mapa, dezena));
        });

        return mapa;
    }

    public Map<String, Object> resumo() {

        List<ConcursoEntity> concursos =
                repository.findAll();

        LOGGER.debug("Calculando resumo estatístico para {} concursos", concursos.size());

        double mediaSoma =
                concursos.stream()
                        .mapToInt(ConcursoEntity::getSoma)
                        .average()
                        .orElse(0);

        double mediaPares =
                concursos.stream()
                        .mapToInt(ConcursoEntity::getPares)
                        .average()
                        .orElse(0);

        double mediaImpares =
                concursos.stream()
                        .mapToInt(ConcursoEntity::getImpares)
                        .average()
                        .orElse(0);

        Map<String, Object> resumo =
                new HashMap<>();

        resumo.put("totalConcursos", concursos.size());
        resumo.put("mediaSoma", mediaSoma);
        resumo.put("mediaPares", mediaPares);
        resumo.put("mediaImpares", mediaImpares);
        resumo.put("frequencia", frequencia(concursos));

        return resumo;
    }

    private Map<Integer, Integer> inicializarMapa() {

        Map<Integer, Integer> mapa =
                new HashMap<>();

        for (int i = 1; i <= 25; i++) {
            mapa.put(i, 0);
        }

        return mapa;
    }

    private void incrementar(
            Map<Integer, Integer> mapa,
            Integer dezena
    ) {

        mapa.put(
                dezena,
                mapa.get(dezena) + 1
        );
    }
}
