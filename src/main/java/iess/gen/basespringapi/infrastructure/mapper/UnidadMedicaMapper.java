/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.mapper;

import iess.gen.basespringapi.application.dto.ProvinciaUnidadesAgrupada;
import iess.gen.basespringapi.infrastructure.controller.dto.ProvinciaUnidadesPublicResponse;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaPublicResponse;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaRequest;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaResponse;
import iess.gen.basespringapi.model.UnidadMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<ProvinciaUnidadesPublicResponse> toPublicResponseList(List<ProvinciaUnidadesAgrupada> agrupadas) {
        if (agrupadas == null) {
            return List.of();
        }
        return agrupadas.stream()
                .map(agrupada -> ProvinciaUnidadesPublicResponse.builder()
                        .provincia(agrupada.getProvincia())
                        .unidades(agrupada.getUnidades().stream()
                                .map(this::toPublicResponse)
                                .toList())
                        .build())
                .toList();
    }
}
