package com.webdeskdata.lotofacilanalyzer.repository;

import com.webdeskdata.lotofacilanalyzer.entity.ConcursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcursoRepository
        extends JpaRepository<ConcursoEntity, Integer> {
}