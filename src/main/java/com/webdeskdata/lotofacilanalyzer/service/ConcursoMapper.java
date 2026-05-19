package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import com.webdeskdata.lotofacilanalyzer.util.EstatisticaUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ConcursoMapper {

    private static final int QUANTIDADE_DEZENAS = 15;

    private static final DateTimeFormatter DATA_CAIXA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConcursoEntity toEntity(CaixaResponseDTO dto) {

        List<Integer> dezenas = normalizarDezenas(dto);
        int pares = contar(dezenas, n -> n % 2 == 0);

        return ConcursoEntity.builder()
                .concurso(dto.getNumero())
                .data(LocalDate.parse(
                        dto.getDataApuracao(),
                        DATA_CAIXA_FORMATTER
                ))
                .d01(dezenas.get(0))
                .d02(dezenas.get(1))
                .d03(dezenas.get(2))
                .d04(dezenas.get(3))
                .d05(dezenas.get(4))
                .d06(dezenas.get(5))
                .d07(dezenas.get(6))
                .d08(dezenas.get(7))
                .d09(dezenas.get(8))
                .d10(dezenas.get(9))
                .d11(dezenas.get(10))
                .d12(dezenas.get(11))
                .d13(dezenas.get(12))
                .d14(dezenas.get(13))
                .d15(dezenas.get(14))
                .pares(pares)
                .impares(QUANTIDADE_DEZENAS - pares)
                .soma(dezenas.stream().mapToInt(Integer::intValue).sum())
                .primos(contar(dezenas, EstatisticaUtil.PRIMOS::contains))
                .fibonacci(contar(dezenas, EstatisticaUtil.FIBONACCI::contains))
                .moldura(contar(dezenas, EstatisticaUtil.MOLDURA::contains))
                .miolo(contar(dezenas, n -> !EstatisticaUtil.MOLDURA.contains(n)))
                .build();
    }

    private List<Integer> normalizarDezenas(CaixaResponseDTO dto) {

        List<Integer> dezenas = dto.getListaDezenas()
                .stream()
                .map(Integer::parseInt)
                .sorted()
                .toList();

        if (dezenas.size() != QUANTIDADE_DEZENAS) {
            throw new IllegalArgumentException(
                    "Concurso " + dto.getNumero() + " possui " + dezenas.size() + " dezenas"
            );
        }

        return dezenas;
    }

    private int contar(
            List<Integer> dezenas,
            Predicate<Integer> predicate
    ) {

        return (int) dezenas.stream()
                .filter(predicate)
                .count();
    }
}
