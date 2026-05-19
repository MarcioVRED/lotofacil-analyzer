package com.webdeskdata.lotofacilanalyzer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "concursos")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConcursoEntity {

    @Id
    private Integer concurso;

    private LocalDate data;

    private Integer d01;
    private Integer d02;
    private Integer d03;
    private Integer d04;
    private Integer d05;
    private Integer d06;
    private Integer d07;
    private Integer d08;
    private Integer d09;
    private Integer d10;
    private Integer d11;
    private Integer d12;
    private Integer d13;
    private Integer d14;
    private Integer d15;

    private Integer soma;
    private Integer pares;
    private Integer impares;
    private Integer primos;
    private Integer fibonacci;
    private Integer moldura;
    private Integer miolo;

    public List<Integer> getDezenas() {
        return List.of(
                d01,
                d02,
                d03,
                d04,
                d05,
                d06,
                d07,
                d08,
                d09,
                d10,
                d11,
                d12,
                d13,
                d14,
                d15
        );
    }
}
