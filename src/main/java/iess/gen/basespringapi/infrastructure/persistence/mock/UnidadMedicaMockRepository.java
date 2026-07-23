/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import iess.gen.basespringapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.basespringapi.infrastructure.config.AppProperties;
import iess.gen.basespringapi.infrastructure.util.ProvinciaNormalizer;
import iess.gen.basespringapi.infrastructure.util.UnidadMedicaIdGenerator;
import iess.gen.basespringapi.model.UnidadMedica;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio en memoria que carga unidades médicas desde el archivo JSON embebido.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Slf4j
@Repository
@Profile("mock")
@RequiredArgsConstructor
public class UnidadMedicaMockRepository implements UnidadMedicaRepositoryPort {

    private final AppProperties appProperties;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    private final Map<UUID, UnidadMedica> storage = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadData() {
        String jsonPath = appProperties.getData().getJsonPath();
        Resource resource = resourceLoader.getResource(jsonPath);

        if (!resource.exists()) {
            log.error("No se encontró el archivo de datos en: {}", jsonPath);
            return;
        }

        try (InputStream inputStream = resource.getInputStream()) {
            List<Map<String, Object>> provinciasList = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Map<String, Object>>>() {}
            );

            int totalCargadas = 0;
            for (Map<String, Object> provMap : provinciasList) {
                String provincia = ProvinciaNormalizer.normalize((String) provMap.get("provincia"));
                List<Map<String, Object>> unidadesList = castUnidades(provMap.get("unidades"));

                for (Map<String, Object> unidadMap : unidadesList) {
                    String siglas = (String) unidadMap.get("siglas");
                    String nombre = (String) unidadMap.get("nombre");
                    UUID id = UnidadMedicaIdGenerator.fromSiglas(siglas, provincia, nombre);

                    UnidadMedica unidad = UnidadMedica.builder()
                            .id(id)
                            .nombre(nombre)
                            .nivel((Integer) unidadMap.get("nivel"))
                            .latitud(toDouble(unidadMap.get("latitud")))
                            .longitud(toDouble(unidadMap.get("longitud")))
                            .descripcion((String) unidadMap.get("descripcion"))
                            .telefono((String) unidadMap.get("telefono"))
                            .sitioWeb((String) unidadMap.get("sitio_web"))
                            .siglas(siglas)
                            .direccion((String) unidadMap.get("direccion"))
                            .provincia(provincia)
                            .status("A")
                            .createdBy("system")
                            .createdAt(LocalDateTime.now())
                            .build();

                    storage.put(id, unidad);
                    totalCargadas++;
                }
            }

            log.info("Se cargaron {} unidades médicas en memoria desde {}", totalCargadas, jsonPath);
        } catch (IOException e) {
            log.error("Error al cargar unidades médicas desde JSON", e);
        }
    }

    @Override
    public List<UnidadMedica> findAllActive() {
        return storage.values().stream()
                .filter(this::isActive)
                .toList();
    }

    @Override
    public Optional<UnidadMedica> findById(UUID id) {
        return Optional.ofNullable(storage.get(id)).filter(this::isActive);
    }

    @Override
    public Optional<UnidadMedica> findBySiglas(String siglas) {
        if (siglas == null || siglas.isBlank()) {
            return Optional.empty();
        }

        String normalizedSiglas = siglas.trim().toUpperCase(Locale.ROOT);
        return storage.values().stream()
                .filter(this::isActive)
                .filter(unidad -> unidad.getSiglas() != null
                        && unidad.getSiglas().trim().toUpperCase(Locale.ROOT).equals(normalizedSiglas))
                .findFirst();
    }

    @Override
    public List<UnidadMedica> search(String termino, String provincia, Integer nivel) {
        String normalizedTerm = termino != null ? termino.trim().toLowerCase(Locale.ROOT) : null;

        return findAllActive().stream()
                .filter(unidad -> provincia == null || provincia.isBlank()
                        || ProvinciaNormalizer.matches(unidad.getProvincia(), provincia))
                .filter(unidad -> nivel == null || nivel.equals(unidad.getNivel()))
                .filter(unidad -> normalizedTerm == null || normalizedTerm.isBlank()
                        || containsIgnoreCase(unidad.getNombre(), normalizedTerm)
                        || containsIgnoreCase(unidad.getSiglas(), normalizedTerm)
                        || containsIgnoreCase(unidad.getDireccion(), normalizedTerm)
                        || containsIgnoreCase(unidad.getProvincia(), normalizedTerm))
                .toList();
    }

    @Override
    public UnidadMedica save(UnidadMedica unidadMedica) {
        if (unidadMedica.getId() == null) {
            unidadMedica.setId(UnidadMedicaIdGenerator.fromSiglas(
                    unidadMedica.getSiglas(), unidadMedica.getProvincia(), unidadMedica.getNombre()));
        }
        storage.put(unidadMedica.getId(), unidadMedica);
        return unidadMedica;
    }

    private boolean isActive(UnidadMedica unidad) {
        return "A".equals(unidad.getStatus());
    }

    private boolean containsIgnoreCase(String value, String term) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(term);
    }

    private Double toDouble(Object value) {
        if (value == null) {
            return null;
        }
        return ((Number) value).doubleValue();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castUnidades(Object unidades) {
        if (unidades instanceof List<?> list) {
            return (List<Map<String, Object>>) list;
        }
        return new ArrayList<>();
    }
}
