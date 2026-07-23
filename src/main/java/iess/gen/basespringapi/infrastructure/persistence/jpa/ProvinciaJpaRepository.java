/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para provincias.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
public interface ProvinciaJpaRepository extends JpaRepository<ProvinciaEntity, Integer> {

    Optional<ProvinciaEntity> findByNomProvinciaIgnoreCaseAndEstRegistro(String nomProvincia, String estRegistro);
}
