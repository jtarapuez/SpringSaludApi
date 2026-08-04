/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.oracle;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para DIR_UNIDADESMED_TP.
 */
@Profile("oracle")
public interface DirUnidadMedTpJpaRepository extends JpaRepository<DirUnidadMedTpEntity, Long> {

    @Query("""
            SELECT u FROM DirUnidadMedTpEntity u
            WHERE u.estado = 'A'
            ORDER BY u.nombreProvincia, u.nombre
            """)
    List<DirUnidadMedTpEntity> findAllActive();

    @Query("""
            SELECT u FROM DirUnidadMedTpEntity u
            WHERE u.estado = 'A' AND u.id = :id
            """)
    Optional<DirUnidadMedTpEntity> findActiveById(@Param("id") Long id);

    @Query("""
            SELECT u FROM DirUnidadMedTpEntity u
            WHERE u.estado = 'A' AND UPPER(u.siglas) = UPPER(:siglas)
            ORDER BY u.nombreProvincia, u.nombre
            """)
    List<DirUnidadMedTpEntity> findActiveBySiglas(@Param("siglas") String siglas);

    @Query("""
            SELECT u FROM DirUnidadMedTpEntity u
            WHERE u.estado = 'A'
            AND (:nivel IS NULL OR u.nivelUm = :nivelStr)
            AND (:provincia IS NULL OR UPPER(u.nombreProvincia) = UPPER(:provincia))
            AND (
                :termino IS NULL OR :termino = ''
                OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(u.siglas) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(u.direccion) LIKE LOWER(CONCAT('%', :termino, '%'))
                OR LOWER(u.nombreProvincia) LIKE LOWER(CONCAT('%', :termino, '%'))
            )
            ORDER BY u.nombreProvincia, u.nombre
            """)
    List<DirUnidadMedTpEntity> searchActive(
            @Param("termino") String termino,
            @Param("provincia") String provincia,
            @Param("nivel") Integer nivel,
            @Param("nivelStr") String nivelStr
    );
}
