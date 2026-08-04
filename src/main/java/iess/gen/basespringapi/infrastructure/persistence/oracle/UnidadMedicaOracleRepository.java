/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.oracle;

import iess.gen.basespringapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.basespringapi.infrastructure.mapper.DirUnidadMedTpMapper;
import iess.gen.basespringapi.infrastructure.util.ProvinciaNormalizer;
import iess.gen.basespringapi.model.UnidadMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia Oracle (DIR_UNIDADESMED_TP @ DBDVP).
 */
@Repository
@Profile("oracle")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnidadMedicaOracleRepository implements UnidadMedicaRepositoryPort {

    private final DirUnidadMedTpJpaRepository jpaRepository;
    private final DirUnidadMedTpMapper mapper;

    @Override
    public List<UnidadMedica> findAllActive() {
        return jpaRepository.findAllActive().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UnidadMedica> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return jpaRepository.findActiveById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UnidadMedica> findBySiglas(String siglas) {
        if (siglas == null || siglas.isBlank()) {
            return Optional.empty();
        }
        return jpaRepository.findActiveBySiglas(siglas.trim()).stream()
                .findFirst()
                .map(mapper::toDomain);
    }

    @Override
    public List<UnidadMedica> search(String termino, String provincia, Integer nivel) {
        String provinciaFiltro = provincia != null && !provincia.isBlank() ? provincia.trim() : null;
        String normalizedTermino = termino != null ? termino.trim() : null;
        if (normalizedTermino != null && normalizedTermino.isBlank()) {
            normalizedTermino = null;
        }
        String nivelStr = nivel != null ? String.valueOf(nivel) : null;

        return jpaRepository.searchActive(normalizedTermino, null, nivel, nivelStr).stream()
                .map(mapper::toDomain)
                .filter(u -> provinciaFiltro == null
                        || ProvinciaNormalizer.matches(u.getProvincia(), provinciaFiltro))
                .toList();
    }

    @Override
    @Transactional
    public UnidadMedica save(UnidadMedica unidadMedica) {
        DirUnidadMedTpEntity entity = mapper.toEntity(unidadMedica);
        if (entity.getEstado() == null) {
            entity.setEstado("A");
        }
        if (entity.getUsuCreacion() == null) {
            entity.setUsuCreacion("API");
        }
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
