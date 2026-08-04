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
import iess.gen.basespringapi.infrastructure.persistence.jpa.ProvinciaEntity;
import iess.gen.basespringapi.infrastructure.persistence.jpa.UnidadMedicaEntity;
import iess.gen.basespringapi.model.UnidadMedica;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Mapper centralizado para conversiones de Unidad Médica.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Component
public class UnidadMedicaMapper {

    public UnidadMedica toDomain(UnidadMedicaEntity entity) {
        if (entity == null) {
            return null;
        }

        String provinciaNombre = entity.getProvincia() != null
                ? entity.getProvincia().getNomProvincia()
                : null;

        return UnidadMedica.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .nivel(entity.getNivel())
                .latitud(toDouble(entity.getLatitud()))
                .longitud(toDouble(entity.getLongitud()))
                .descripcion(entity.getDescripcion())
                .telefono(entity.getTelefono())
                .sitioWeb(entity.getSitioWeb())
                .siglas(entity.getSiglas())
                .direccion(entity.getDireccion())
                .provincia(provinciaNombre)
                .status(entity.getEstRegistro())
                .createdBy(entity.getUsuCreacion())
                .createdAt(entity.getFecCreacion())
                .updatedBy(entity.getUsuActualizacion())
                .updatedAt(entity.getFecActualizacion())
                .deletedBy(entity.getUsuEliminacion())
                .deletedAt(entity.getFecEliminacion())
                .build();
    }

    public UnidadMedicaEntity toEntity(UnidadMedica domain, ProvinciaEntity provincia) {
        if (domain == null) {
            return null;
        }

        return UnidadMedicaEntity.builder()
                .id(domain.getId())
                .provincia(provincia)
                .nombre(domain.getNombre())
                .nivel(domain.getNivel())
                .latitud(toBigDecimal(domain.getLatitud()))
                .longitud(toBigDecimal(domain.getLongitud()))
                .descripcion(domain.getDescripcion())
                .telefono(domain.getTelefono())
                .sitioWeb(domain.getSitioWeb())
                .siglas(domain.getSiglas())
                .direccion(domain.getDireccion())
                .estRegistro(domain.getStatus() != null ? domain.getStatus() : "A")
                .usuCreacion(domain.getCreatedBy())
                .fecCreacion(domain.getCreatedAt())
                .usuActualizacion(domain.getUpdatedBy())
                .fecActualizacion(domain.getUpdatedAt())
                .usuEliminacion(domain.getDeletedBy())
                .fecEliminacion(domain.getDeletedAt())
                .build();
    }

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

    private Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    private BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}
