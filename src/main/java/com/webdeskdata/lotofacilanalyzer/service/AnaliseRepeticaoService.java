package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.AnaliseRepeticaoResponse;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.repository.ConcursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliseRepeticaoService {

    private final ConcursoRepository concursoRepository;

    public List<AnaliseRepeticaoResponse> buscarConcursosParecidos(Integer minimoIguais) {

        List<ConcursoEntity> concursos = concursoRepository.findAll();

        List<AnaliseRepeticaoResponse> resultado = new ArrayList<>();

        for (int i = 0; i < concursos.size(); i++) {

            ConcursoEntity c1 = concursos.get(i);
            BitSet jogo1 = gerarBitSet(c1);

            for (int j = i + 1; j < concursos.size(); j++) {

                ConcursoEntity c2 = concursos.get(j);
                BitSet jogo2 = gerarBitSet(c2);

                BitSet clone = (BitSet) jogo1.clone();
                clone.and(jogo2);

                int iguais = clone.cardinality();

                if (iguais >= minimoIguais) {

                    resultado.add(
                            new AnaliseRepeticaoResponse(
                                    c1.getConcurso(),
                                    c2.getConcurso(),
                                    c1.getData(),
                                    c2.getData(),
                                    iguais,
                                    obterDezenasCoincidentes(c1, c2),
                                    obterDiferentesConcurso1(c1, c2),
                                    obterDiferentesConcurso2(c1, c2)
                            )
                    );
                }
            }
        }

        return resultado;
    }

    private BitSet gerarBitSet(ConcursoEntity concurso) {

        BitSet bitSet = new BitSet(26);

        bitSet.set(concurso.getD01());
        bitSet.set(concurso.getD02());
        bitSet.set(concurso.getD03());
        bitSet.set(concurso.getD04());
        bitSet.set(concurso.getD05());
        bitSet.set(concurso.getD06());
        bitSet.set(concurso.getD07());
        bitSet.set(concurso.getD08());
        bitSet.set(concurso.getD09());
        bitSet.set(concurso.getD10());
        bitSet.set(concurso.getD11());
        bitSet.set(concurso.getD12());
        bitSet.set(concurso.getD13());
        bitSet.set(concurso.getD14());
        bitSet.set(concurso.getD15());

        return bitSet;
    }

    private List<Integer> obterDezenasCoincidentes(
            ConcursoEntity c1,
            ConcursoEntity c2
    ) {

        List<Integer> dezenas1 = List.of(
                c1.getD01(), c1.getD02(), c1.getD03(),
                c1.getD04(), c1.getD05(), c1.getD06(),
                c1.getD07(), c1.getD08(), c1.getD09(),
                c1.getD10(), c1.getD11(), c1.getD12(),
                c1.getD13(), c1.getD14(), c1.getD15()
        );

        List<Integer> dezenas2 = List.of(
                c2.getD01(), c2.getD02(), c2.getD03(),
                c2.getD04(), c2.getD05(), c2.getD06(),
                c2.getD07(), c2.getD08(), c2.getD09(),
                c2.getD10(), c2.getD11(), c2.getD12(),
                c2.getD13(), c2.getD14(), c2.getD15()
        );

        return dezenas1.stream()
                .filter(dezenas2::contains)
                .sorted()
                .toList();
    }

    private List<Integer> obterDiferentesConcurso1(
            ConcursoEntity c1,
            ConcursoEntity c2
    ) {

        List<Integer> dezenas1 = obterDezenas(c1);
        List<Integer> dezenas2 = obterDezenas(c2);

        return dezenas1.stream()
                .filter(d -> !dezenas2.contains(d))
                .sorted()
                .toList();
    }

    private List<Integer> obterDiferentesConcurso2(
            ConcursoEntity c1,
            ConcursoEntity c2
    ) {

        List<Integer> dezenas1 = obterDezenas(c1);
        List<Integer> dezenas2 = obterDezenas(c2);

        return dezenas2.stream()
                .filter(d -> !dezenas1.contains(d))
                .sorted()
                .toList();
    }

    private List<Integer> obterDezenas(ConcursoEntity c) {

        return List.of(
                c.getD01(), c.getD02(), c.getD03(),
                c.getD04(), c.getD05(), c.getD06(),
                c.getD07(), c.getD08(), c.getD09(),
                c.getD10(), c.getD11(), c.getD12(),
                c.getD13(), c.getD14(), c.getD15()
        );
    }
}