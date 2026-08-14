/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.controller.advice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleIllegalArgument_shouldReturnNotFound() {
        ResponseEntity<?> response = handler.handleIllegalArgument(new IllegalArgumentException("No existe"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).hasFieldOrPropertyWithValue("message", "No existe");
    }

    @Test
    void handleNotFound_shouldReturnNotFoundMessage() {
        ResponseEntity<?> response = handler.handleNotFound(
                new NoResourceFoundException(HttpMethod.GET, "/api/desconocido"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).hasFieldOrPropertyWithValue("message", "Ruta no encontrada");
    }

    @Test
    void handleTypeMismatch_shouldReturnBadRequest() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", Integer.class, "nivel", null, new NumberFormatException("bad"));

        ResponseEntity<?> response = handler.handleTypeMismatch(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).hasFieldOrPropertyWithValue("message", "Parámetro inválido: nivel");
    }

    @Test
    void handleGeneric_shouldReturnInternalServerError() {
        ResponseEntity<?> response = handler.handleGeneric(new RuntimeException("fallo"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).hasFieldOrPropertyWithValue("message", "Error interno del servidor");
    }
}
