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
 * JPA solo para entidades PostgreSQL (esquema salud).
 */
@Configuration
@Profile("postgres")
@EntityScan(basePackages = "iess.gen.basespringapi.infrastructure.persistence.jpa")
@EnableJpaRepositories(basePackages = "iess.gen.basespringapi.infrastructure.persistence.jpa")
public class PostgresJpaConfig {
}
