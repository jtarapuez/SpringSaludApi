/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * <b> Clase principal de arranque para la API Base de Spring Boot. </b>
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@SpringBootApplication
@EntityScan(basePackages = "iess.gen.saludgeolocalizacionapi.infrastructure.persistence.placeholder")
public class SaludGeolocalizacionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaludGeolocalizacionApiApplication.class, args);
    }
}
