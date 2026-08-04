/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA solo para entidades Oracle (DIR_UNIDADESMED_TP).
 */
@Configuration
@Profile("oracle")
@EntityScan(basePackages = "iess.gen.basespringapi.infrastructure.persistence.oracle")
@EnableJpaRepositories(basePackages = "iess.gen.basespringapi.infrastructure.persistence.oracle")
public class OracleJpaConfig {
}
