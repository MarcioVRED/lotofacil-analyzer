package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;

final class DatasetExportMapper {

    static final String[] HEADERS = {
            "concurso",
            "data",
            "d01",
            "d02",
            "d03",
            "d04",
            "d05",
            "d06",
            "d07",
            "d08",
            "d09",
            "d10",
            "d11",
            "d12",
            "d13",
            "d14",
            "d15",
            "soma",
            "pares",
            "impares",
            "primos",
            "fibonacci",
            "moldura",
            "miolo"
    };

    private DatasetExportMapper() {
    }

    static Object[] values(ConcursoEntity concurso) {

        return new Object[]{
                concurso.getConcurso(),
                concurso.getData().toString(),
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
                concurso.getD15(),
                concurso.getSoma(),
                concurso.getPares(),
                concurso.getImpares(),
                concurso.getPrimos(),
                concurso.getFibonacci(),
                concurso.getMoldura(),
                concurso.getMiolo()
        };
    }
}
