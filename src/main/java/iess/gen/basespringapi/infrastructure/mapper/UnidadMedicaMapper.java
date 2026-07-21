/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.mapper;

import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaPublicResponse;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaRequest;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaResponse;
import iess.gen.basespringapi.infrastructure.persistence.jpa.UnidadMedicaEntity;
import iess.gen.basespringapi.model.UnidadMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <b> Mapper centralizado para realizar las conversiones de Unidad Médica. </b>
 *
 * @author Juan Carlos Estévez Hidalgo
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Carlos Estévez Hidalgo , Date: 18 jun 2026]
 * </p>
 */
@Component
public class UnidadMedicaMapper {

    /**
     * Convierte una entidad de base de datos al modelo de dominio.
     *
     * @param entity Entidad de persistencia.
     * @return Modelo de dominio.
     */
    public UnidadMedica toDomain(UnidadMedicaEntity entity) {
        if (entity == null) {
            return null;
        }
        return UnidadMedica.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .nivel(entity.getNivel())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .descripcion(entity.getDescripcion())
                .telefono(entity.getTelefono())
                .sitioWeb(entity.getSitioWeb())
                .siglas(entity.getSiglas())
                .direccion(entity.getDireccion())
                .provincia(entity.getProvincia())
                .status(entity.getStatus())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())
                .deletedBy(entity.getDeletedBy())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    /**
     * Convierte el modelo de dominio a una entidad de persistencia.
     *
     * @param domain Modelo de dominio.
     * @return Entidad de persistencia.
     */
    public UnidadMedicaEntity toEntity(UnidadMedica domain) {
        if (domain == null) {
            return null;
        }
        return UnidadMedicaEntity.builder()
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

    /**
     * Convierte un DTO de Request a un modelo de dominio.
     *
     * @param request DTO con datos de entrada.
     * @return Modelo de dominio.
     */
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

    /**
     * Convierte el modelo de dominio a un DTO de Response.
     *
     * @param domain Modelo de dominio.
     * @return DTO con datos de salida.
     */
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
}
