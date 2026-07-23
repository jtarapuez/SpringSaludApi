/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.application.port;

import iess.gen.basespringapi.model.UnidadMedica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de persistencia que define el contrato para el repositorio de Unidades Médicas.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
public interface UnidadMedicaRepositoryPort {

    List<UnidadMedica> findAllActive();

    Optional<UnidadMedica> findById(UUID id);

    Optional<UnidadMedica> findBySiglas(String siglas);

    List<UnidadMedica> search(String termino, String provincia, Integer nivel);

    UnidadMedica save(UnidadMedica unidadMedica);
}
