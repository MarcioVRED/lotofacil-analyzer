package com.webdeskdata.lotofacilanalyzer.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EstatisticaUtilTest {

    @Test
    void conjuntosPossuemNumerosEsperadosDaLotofacil() {

        assertThat(EstatisticaUtil.PRIMOS)
                .containsExactlyInAnyOrder(2, 3, 5, 7, 11, 13, 17, 19, 23);

        assertThat(EstatisticaUtil.FIBONACCI)
                .containsExactlyInAnyOrder(1, 2, 3, 5, 8, 13, 21);

        assertThat(EstatisticaUtil.MOLDURA)
                .containsExactlyInAnyOrder(
                        1, 2, 3, 4, 5,
                        6, 10,
                        11, 15,
                        16, 20,
                        21, 22, 23, 24, 25
                );
    }
}
