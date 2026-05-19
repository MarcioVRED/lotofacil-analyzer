package com.webdeskdata.lotofacilanalyzer.service;

import com.webdeskdata.lotofacilanalyzer.dto.CaixaResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaixaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CaixaService.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public CaixaService(
            RestTemplate restTemplate,
            @Value("${lotofacil.caixa.base-url}") String baseUrl
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = normalizarBaseUrl(baseUrl);
    }

    public CaixaResponseDTO obterConcurso(Integer numero) {

        LOGGER.debug("Consultando concurso {} na API da Caixa", numero);

        return restTemplate.getForObject(
                baseUrl + numero,
                CaixaResponseDTO.class
        );
    }

    private String normalizarBaseUrl(String url) {

        if (url.endsWith("/")) {
            return url;
        }

        return url + "/";
    }
}
