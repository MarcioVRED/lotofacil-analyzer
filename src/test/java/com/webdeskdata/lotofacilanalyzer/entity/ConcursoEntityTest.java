package com.webdeskdata.lotofacilanalyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ConcursoEntityTest {

    @Test
    void builderGettersESettersFuncionam() throws Exception {

        ConcursoEntity entity = ConcursoEntity.builder()
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
                .d15(25)
                .soma(200)
                .pares(8)
                .impares(7)
                .build();

        entity.setMiolo(5);

        assertThat(entity.getConcurso()).isEqualTo(3210);
        assertThat(entity.getData()).isEqualTo(LocalDate.of(2026, 5, 18));
        assertThat(entity.getD01()).isEqualTo(1);
        assertThat(entity.getD15()).isEqualTo(25);
        assertThat(entity.getSoma()).isEqualTo(200);
        assertThat(entity.getPares()).isEqualTo(8);
        assertThat(entity.getImpares()).isEqualTo(7);
        assertThat(entity.getMiolo()).isEqualTo(5);
        assertThat(entity.getDezenas())
                .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 25);
    }

    @Test
    void possuiMapeamentoJpaEsperado() throws Exception {

        assertThat(ConcursoEntity.class).hasAnnotation(Entity.class);
        assertThat(ConcursoEntity.class.getAnnotation(Table.class).name())
                .isEqualTo("concursos");
        assertThat(ConcursoEntity.class.getDeclaredField("concurso").getAnnotation(Id.class))
                .isNotNull();
    }
}
