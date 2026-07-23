/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.application.usecase;

import iess.gen.basespringapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.basespringapi.infrastructure.controller.dto.ProvinciaUnidadesPublicResponse;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaResponse;
import iess.gen.basespringapi.infrastructure.mapper.UnidadMedicaMapper;
import iess.gen.basespringapi.model.UnidadMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de uso para gestionar la lógica de negocio de las Unidades Médicas.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UnidadMedicaUseCase {

    private final UnidadMedicaRepositoryPort repository;
    private final UnidadMedicaMapper mapper;

    public List<ProvinciaUnidadesPublicResponse> obtenerUnidadesAgrupadas() {
        return agruparPorProvincia(repository.findAllActive());
    }

    public List<ProvinciaUnidadesPublicResponse> buscarUnidades(String termino, String provincia, Integer nivel) {
        return agruparPorProvincia(repository.search(termino, provincia, nivel));
    }

    public UnidadMedicaResponse buscarPorId(UUID id) {
        UnidadMedica unidad = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con ID: " + id));
        return mapper.toResponse(unidad);
    }

    public UnidadMedicaResponse buscarPorSiglas(String siglas) {
        UnidadMedica unidad = repository.findBySiglas(siglas)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con siglas: " + siglas));
        return mapper.toResponse(unidad);
    }

    private List<ProvinciaUnidadesPublicResponse> agruparPorProvincia(List<UnidadMedica> unidades) {
        return unidades.stream()
                .collect(Collectors.groupingBy(UnidadMedica::getProvincia))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> ProvinciaUnidadesPublicResponse.builder()
                        .provincia(entry.getKey())
                        .unidades(entry.getValue().stream()
                                .sorted(Comparator.comparing(UnidadMedica::getNombre))
                                .map(mapper::toPublicResponse)
                                .toList())
                        .build())
                .toList();
    }
}
