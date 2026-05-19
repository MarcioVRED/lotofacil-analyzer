package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoRequest;
import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoResponse;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VerificarJogoService {

    private final ConcursoRepository concursoRepository;

    public VerificarJogoResponse verificar(VerificarJogoRequest request) {

        Set<Integer> jogoInformado = new HashSet<>(request.dezenas());

        List<ConcursoEntity> concursos = concursoRepository.findAll();

        for (ConcursoEntity concurso : concursos) {

            Set<Integer> dezenasConcurso = Set.of(
                    concurso.getD01(),
                    concurso.getD02(),
                    concurso.getD03(),
                    concurso.getD04(),
                    concurso.getD05(),
                    concurso.getD06(),
                    concurso.getD07(),
                    concurso.getD08(),
                    concurso.getD09(),
                    concurso.getD10(),
                    concurso.getD11(),
                    concurso.getD12(),
                    concurso.getD13(),
                    concurso.getD14(),
                    concurso.getD15()
            );

            if (dezenasConcurso.equals(jogoInformado)) {

                return new VerificarJogoResponse(
                        true,
                        concurso.getConcurso(),
                        concurso.getData()
                );
            }
        }

        return new VerificarJogoResponse(
                false,
                null,
                null
        );
    }
}