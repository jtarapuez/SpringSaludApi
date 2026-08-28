/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.mapper;

import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.ProvinciaUnidadesPublicResponse;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaPublicResponse;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaRequest;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaResponse;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper centralizado para conversiones de Unidad Médica (dominio ↔ DTO).
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Component
public class UnidadMedicaMapper {

    public UnidadMedica toDomain(UnidadMedicaRequest request) {
        if (request == null) {
            return null;
        }
        return UnidadMedica.builder()
                .nombre(request.getNombre())
                .nivel(request.getNivel())
                .latitud(request.getLatitud())
                .longitud(request.getLongitud())
                .descripcion(request.getDescripcion())
                .telefono(request.getTelefono())
                .sitioWeb(request.getSitioWeb())
                .siglas(request.getSiglas())
                .direccion(request.getDireccion())
                .status("A")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UnidadMedicaResponse toResponse(UnidadMedica domain) {
        if (domain == null) {
            return null;
        }
        return UnidadMedicaResponse.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .nivel(domain.getNivel())
                .latitud(domain.getLatitud())
                .longitud(domain.getLongitud())
                .descripcion(domain.getDescripcion())
                .telefono(domain.getTelefono())
                .sitioWeb(domain.getSitioWeb())
                .siglas(domain.getSiglas())
                .direccion(domain.getDireccion())
                .status(domain.getStatus())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .deletedBy(domain.getDeletedBy())
                .deletedAt(domain.getDeletedAt())
                .build();
    }

    public UnidadMedicaPublicResponse toPublicResponse(UnidadMedica domain) {
        if (domain == null) {
            return null;
        }
        return UnidadMedicaPublicResponse.builder()
                .nombre(domain.getNombre())
                .nivel(domain.getNivel())
                .latitud(domain.getLatitud())
                .longitud(domain.getLongitud())
                .descripcion(domain.getDescripcion())
                .telefono(domain.getTelefono())
                .sitioWeb(domain.getSitioWeb())
                .siglas(domain.getSiglas())
                .direccion(domain.getDireccion())
                .build();
    }

    /**
     * Agrupa unidades de dominio por provincia y las convierte al DTO REST público.
     *
     * @param unidades listado de dominio
     * @return listado agrupado listo para el controlador
     */
    public List<ProvinciaUnidadesPublicResponse> toPublicResponseList(List<UnidadMedica> unidades) {
        if (unidades == null || unidades.isEmpty()) {
            return List.of();
        }
        return unidades.stream()
                .collect(Collectors.groupingBy(UnidadMedica::getProvincia))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(entry -> ProvinciaUnidadesPublicResponse.builder()
                        .provincia(entry.getKey())
                        .unidades(entry.getValue().stream()
                                .sorted(Comparator.comparing(UnidadMedica::getNombre))
                                .map(this::toPublicResponse)
                                .toList())
                        .build())
                .toList();
    }
}
