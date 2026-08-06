/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS para permitir peticiones desde el frontend Angular.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AppProperties appProperties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                var cors = appProperties.getCors();
                var mapping = registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");

                if (!cors.getAllowedOriginPatterns().isEmpty()) {
                    mapping.allowedOriginPatterns(cors.getAllowedOriginPatterns().toArray(String[]::new));
                } else if (!cors.getAllowedOrigins().isEmpty()) {
                    mapping.allowedOrigins(cors.getAllowedOrigins().toArray(String[]::new));
                }
            }
        };
    }
}
