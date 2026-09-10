/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Coverage for {@link OpenApiConfig} — relative server for IP access.
 */
class OpenApiConfigTest {

    private final OpenApiConfig openApiConfig = new OpenApiConfig();

    @Test
    void saludGeolocalizacionApiOpenAPI_shouldExposeRelativeServerFirst() {
        OpenAPI openAPI = openApiConfig.saludGeolocalizacionApiOpenAPI();

        assertThat(openAPI.getInfo().getTitle()).isEqualTo("salud-geolocalizacion-api - IESS Salud");
        assertThat(openAPI.getServers()).hasSize(2);
        assertThat(openAPI.getServers().get(0).getUrl()).isEqualTo("/api");
        assertThat(openAPI.getServers().get(1).getUrl()).isEqualTo("http://localhost:8080/api");
    }
}
