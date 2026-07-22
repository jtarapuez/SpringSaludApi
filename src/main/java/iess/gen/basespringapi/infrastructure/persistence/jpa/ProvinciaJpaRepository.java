/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para provincias.
 */
public interface ProvinciaJpaRepository extends JpaRepository<ProvinciaEntity, Integer> {

    Optional<ProvinciaEntity> findByNomProvinciaIgnoreCaseAndEstRegistro(String nomProvincia, String estRegistro);
}
