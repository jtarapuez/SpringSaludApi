/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de documentación OpenAPI / Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseSpringApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BaseSpringApi - IESS Salud")
                        .description("API REST para el mapa de geolocalización de unidades médicas IESS")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("IESS - Equipo de Desarrollo")
                                .email("desarrollo@iess.gob.ec")))
                .servers(List.of(
                        new Server().url("http://localhost:8080/api").description("Desarrollo local")
                ));
    }
}
