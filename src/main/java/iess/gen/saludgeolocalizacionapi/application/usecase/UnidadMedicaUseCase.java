/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.application.usecase;

import iess.gen.saludgeolocalizacionapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UnidadMedica> obtenerUnidadesActivas() {
        return repository.findAllActive();
    }

    public List<UnidadMedica> buscarUnidades(String termino, String provincia, Integer nivel) {
        return repository.search(termino, provincia, nivel);
    }

    public UnidadMedica buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con ID: " + id));
    }

    public UnidadMedica buscarPorSiglas(String siglas) {
        return repository.findBySiglas(siglas)
                .orElseThrow(() -> new IllegalArgumentException("No existe la unidad médica con siglas: " + siglas));
    }
}
