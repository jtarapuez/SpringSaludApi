/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import iess.gen.basespringapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.basespringapi.infrastructure.mapper.UnidadMedicaMapper;
import iess.gen.basespringapi.infrastructure.util.ProvinciaNormalizer;
import iess.gen.basespringapi.model.UnidadMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de persistencia PostgreSQL para unidades médicas (perfil postgres).
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Repository
@Profile("postgres")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnidadMedicaPostgresRepository implements UnidadMedicaRepositoryPort {

    private final UnidadMedicaJpaRepository unidadMedicaJpaRepository;
    private final ProvinciaJpaRepository provinciaJpaRepository;
    private final UnidadMedicaMapper mapper;

    @Override
    public List<UnidadMedica> findAllActive() {
        return unidadMedicaJpaRepository.findAllActiveWithProvincia().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UnidadMedica> findById(UUID id) {
        return unidadMedicaJpaRepository.findActiveById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UnidadMedica> findBySiglas(String siglas) {
        if (siglas == null || siglas.isBlank()) {
            return Optional.empty();
        }

        return unidadMedicaJpaRepository.findActiveBySiglas(siglas.trim()).stream()
                .findFirst()
                .map(mapper::toDomain);
    }

    @Override
    public List<UnidadMedica> search(String termino, String provincia, Integer nivel) {
        String normalizedProvincia = normalizeProvinciaFilter(provincia);
        String normalizedTermino = termino != null ? termino.trim() : null;
        if (normalizedTermino != null && normalizedTermino.isBlank()) {
            normalizedTermino = null;
        }

        return unidadMedicaJpaRepository.searchActive(normalizedTermino, normalizedProvincia, nivel).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public UnidadMedica save(UnidadMedica unidadMedica) {
        ProvinciaEntity provincia = resolveProvincia(unidadMedica.getProvincia());
        UnidadMedicaEntity entity = mapper.toEntity(unidadMedica, provincia);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getEstRegistro() == null) {
            entity.setEstRegistro("A");
        }
        if (entity.getUsuCreacion() == null) {
            entity.setUsuCreacion("system");
        }
        if (entity.getFecCreacion() == null) {
            entity.setFecCreacion(LocalDateTime.now());
        }

        return mapper.toDomain(unidadMedicaJpaRepository.save(entity));
    }

    private ProvinciaEntity resolveProvincia(String provinciaNombre) {
        String normalized = ProvinciaNormalizer.normalize(provinciaNombre);
        return provinciaJpaRepository.findByNomProvinciaIgnoreCaseAndEstRegistro(normalized, "A")
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la provincia: " + provinciaNombre));
    }

    private String normalizeProvinciaFilter(String provincia) {
        if (provincia == null || provincia.isBlank()) {
            return null;
        }
        return ProvinciaNormalizer.normalize(provincia);
    }
}
