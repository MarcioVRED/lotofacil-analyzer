package com.webdeskdata.lotofacilanalyzer.repository;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;

import static org.assertj.core.api.Assertions.assertThat;

class ConcursoRepositoryTest {

    @Test
    void contratoDoRepositoryUsaEntidadeEIdEsperados() {

        assertThat(JpaRepository.class).isAssignableFrom(ConcursoRepository.class);

        ParameterizedType jpaRepository =
                (ParameterizedType) ConcursoRepository.class.getGenericInterfaces()[0];

        assertThat(jpaRepository.getRawType()).isEqualTo(JpaRepository.class);
        assertThat(jpaRepository.getActualTypeArguments())
                .containsExactly(ConcursoEntity.class, Integer.class);
    }
}
