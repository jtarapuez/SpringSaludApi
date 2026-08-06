/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cobertura de {@link OpenApiConfig} — servidor relativo para acceso por IP.
 */
class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void baseSpringApiOpenAPI_shouldExposeRelativeServerFirst() {
        OpenAPI openAPI = openApiConfig.baseSpringApiOpenAPI();

        assertThat(openAPI.getInfo().getTitle()).isEqualTo("BaseSpringApi - IESS Salud");
        assertThat(openAPI.getServers()).hasSize(2);
        assertThat(openAPI.getServers().get(0).getUrl()).isEqualTo("/api");
        assertThat(openAPI.getServers().get(1).getUrl()).isEqualTo("http://localhost:8080/api");
    }
}
