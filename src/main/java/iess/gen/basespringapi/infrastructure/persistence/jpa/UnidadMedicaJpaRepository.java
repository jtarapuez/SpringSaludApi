/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para unidades médicas.
 */
public interface UnidadMedicaJpaRepository extends JpaRepository<UnidadMedicaEntity, UUID> {

    @Query("""
            SELECT u FROM UnidadMedicaEntity u
            JOIN FETCH u.provincia p
            WHERE u.estRegistro = 'A'
            ORDER BY p.nomProvincia, u.nombre
            """)
    List<UnidadMedicaEntity> findAllActiveWithProvincia();

    @Query("""
            SELECT u FROM UnidadMedicaEntity u
            JOIN FETCH u.provincia p
            WHERE u.estRegistro = 'A' AND u.id = :id
            """)
    Optional<UnidadMedicaEntity> findActiveById(@Param("id") UUID id);

    @Query("""
            SELECT u FROM UnidadMedicaEntity u
            JOIN FETCH u.provincia p
            WHERE u.estRegistro = 'A' AND UPPER(u.siglas) = UPPER(:siglas)
            ORDER BY p.nomProvincia
            """)
    List<UnidadMedicaEntity> findActiveBySiglas(@Param("siglas") String siglas);

    @Query("""
            SELECT u FROM UnidadMedicaEntity u
            JOIN FETCH u.provincia p
            WHERE u.estRegistro = 'A'
            AND (:nivel IS NULL OR u.nivel = :nivel)
            AND (:provincia IS NULL OR UPPER(p.nomProvincia) = UPPER(:provincia))
            AND (
                :termino IS NULL OR :termino = ''
                OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(u.siglas) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(u.direccion) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(p.nomProvincia) LIKE LOWER(CONCAT('%', :termino, '%'))
            )
            ORDER BY p.nomProvincia, u.nombre
            """)
    List<UnidadMedicaEntity> searchActive(
            @Param("termino") String termino,
            @Param("provincia") String provincia,
            @Param("nivel") Integer nivel
    );
}
