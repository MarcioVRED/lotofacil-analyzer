package com.webdeskdata.lotofacilanalyzer.controller;

import com.webdeskdata.lotofacilanalyzer.dto.AnaliseRepeticaoResponse;
import com.webdeskdata.lotofacilanalyzer.service.AnaliseRepeticaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analise/repeticao")
@RequiredArgsConstructor
public class AnaliseRepeticaoController {

    private final AnaliseRepeticaoService analiseRepeticaoService;

    @GetMapping
    public List<AnaliseRepeticaoResponse> buscarConcursosParecidos(
            @RequestParam(defaultValue = "12") Integer iguais
    ) {

        return analiseRepeticaoService.buscarConcursosParecidos(iguais);
    }
}