/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Cobertura de {@link CorsConfig} — patrones LAN y fallback a orígenes fijos.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class CorsConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void preflight_fromLanOrigin_shouldAllowCors() throws Exception {
        mockMvc.perform(options("/health")
                        .header("Origin", "http://192.168.12.64:4200")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://192.168.12.64:4200"));
    }

    @Test
    void addCorsMappings_withOriginPatterns_shouldNotFail() {
        AppProperties properties = new AppProperties();
        properties.getCors().setAllowedOriginPatterns(List.of("http://192.168.*.*:4200"));

        assertThatCode(() -> registerCors(properties)).doesNotThrowAnyException();
    }

    @Test
    void addCorsMappings_withAllowedOriginsOnly_shouldNotFail() {
        AppProperties properties = new AppProperties();
        properties.getCors().setAllowedOriginPatterns(List.of());
        properties.getCors().setAllowedOrigins(List.of("http://localhost:4200"));

        assertThatCode(() -> registerCors(properties)).doesNotThrowAnyException();
    }

    private static void registerCors(AppProperties properties) {
        WebMvcConfigurer configurer = new CorsConfig(properties).corsConfigurer();
        configurer.addCorsMappings(new CorsRegistry());
    }
}
