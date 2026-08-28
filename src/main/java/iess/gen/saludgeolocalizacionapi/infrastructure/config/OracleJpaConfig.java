/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <b> Configuración JPA exclusiva para entidades Oracle de DIR_UNIDADESMED_TP. </b>
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 25 ago 2026]
 * </p>
 */
@Configuration
@Profile("oracle")
@EntityScan(basePackages = "iess.gen.saludgeolocalizacionapi.infrastructure.persistence.oracle")
@EnableJpaRepositories(basePackages = "iess.gen.saludgeolocalizacionapi.infrastructure.persistence.oracle")
public class OracleJpaConfig {
}
