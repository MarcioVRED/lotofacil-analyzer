package com.webdeskdata.lotofacilanalyzer.controller;

import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoRequest;
import com.webdeskdata.lotofacilanalyzer.dto.VerificarJogoResponse;
import com.webdeskdata.lotofacilanalyzer.service.VerificarJogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concursos")
@RequiredArgsConstructor
public class VerificarJogoController {

    private final VerificarJogoService verificarJogoService;

    @PostMapping("/verificar-jogo")
    public VerificarJogoResponse verificarJogo(
            @RequestBody VerificarJogoRequest request
    ) {

        return verificarJogoService.verificar(request);
    }
}