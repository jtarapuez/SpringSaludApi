/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.application.usecase;

import iess.gen.basespringapi.application.dto.ProvinciaUnidadesAgrupada;
import iess.gen.basespringapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.basespringapi.model.UnidadMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public List<ProvinciaUnidadesAgrupada> obtenerUnidadesAgrupadas() {
        return agruparPorProvincia(repository.findAllActive());
    }

    public List<ProvinciaUnidadesAgrupada> buscarUnidades(String termino, String provincia, Integer nivel) {
        return agruparPorProvincia(repository.search(termino, provincia, nivel));
    }

    public UnidadMedica buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con ID: " + id));
    }

    public UnidadMedica buscarPorSiglas(String siglas) {
        return repository.findBySiglas(siglas)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con siglas: " + siglas));
    }

    private List<ProvinciaUnidadesAgrupada> agruparPorProvincia(List<UnidadMedica> unidades) {
        return unidades.stream()
                .collect(Collectors.groupingBy(UnidadMedica::getProvincia))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> ProvinciaUnidadesAgrupada.builder()
                        .provincia(entry.getKey())
                        .unidades(entry.getValue().stream()
                                .sorted(Comparator.comparing(UnidadMedica::getNombre))
                                .toList())
                        .build())
                .toList();
    }
}
