/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */

package iess.gen.saludgeolocalizacionapi.infrastructure.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RootControllerTest {

    private final RootController controller = new RootController();

    @Test
    void root_shouldExposeApplicationMetadata() {
        var response = controller.root();

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody())
                .containsEntry("application", "salud-geolocalizacion-api - IESS Salud")
                .containsEntry("status", "UP")
                .containsKey("endpoints");
    }
}
