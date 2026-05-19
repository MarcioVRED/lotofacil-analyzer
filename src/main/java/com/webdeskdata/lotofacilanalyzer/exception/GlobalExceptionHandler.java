package com.webdeskdata.lotofacilanalyzer.exception;

import com.webdeskdata.lotofacilanalyzer.dto.ApiErrorDTO;
import com.webdeskdata.lotofacilanalyzer.dto.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        LOGGER.warn("Requisição inválida em {}", request.getRequestURI(), ex);

        return error(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                "Requisição inválida.",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleRestClient(
            RestClientException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro ao consultar serviço externo em {}", request.getRequestURI(), ex);

        return error(
                HttpStatus.BAD_GATEWAY,
                "EXTERNAL_SERVICE_ERROR",
                "Erro ao consultar serviço externo.",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleDataAccess(
            DataAccessException ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro de banco de dados em {}", request.getRequestURI(), ex);

        return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "DATABASE_ERROR",
                "Erro ao acessar o banco de dados.",
                ex.getMostSpecificCause().getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        LOGGER.error("Erro inesperado em {}", request.getRequestURI(), ex);

        return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Erro interno inesperado.",
                ex.getMessage(),
                request
        );
    }

    private ResponseEntity<ApiResponseDTO<Void>> error(
            HttpStatus status,
            String codigo,
            String mensagem,
            String detalhe,
            HttpServletRequest request
    ) {
        ApiErrorDTO erro = new ApiErrorDTO(
                codigo,
                status.value(),
                List.of(detalhe == null ? mensagem : detalhe)
        );

        return ResponseEntity.status(status)
                .body(ApiResponseDTO.erro(
                        mensagem,
                        erro,
                        request.getRequestURI()
                ));
    }
}
