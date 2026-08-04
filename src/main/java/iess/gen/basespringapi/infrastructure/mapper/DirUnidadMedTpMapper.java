/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.mapper;

import iess.gen.basespringapi.infrastructure.persistence.oracle.DirUnidadMedTpEntity;
import iess.gen.basespringapi.infrastructure.util.UnidadMedicaIdGenerator;
import iess.gen.basespringapi.model.UnidadMedica;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Mapeo entre DIR_UNIDADESMED_TP (Oracle) y el dominio UnidadMedica.
 */
@Component
public class DirUnidadMedTpMapper {

    public UnidadMedica toDomain(DirUnidadMedTpEntity entity) {
        if (entity == null) {
            return null;
        }

        String provincia = entity.getNombreProvincia();
        String nombre = entity.getNombre();
        String siglas = entity.getSiglas();

        return UnidadMedica.builder()
                .id(UnidadMedicaIdGenerator.fromSiglas(siglas, provincia, nombre))
                .nombre(nombre)
                .nivel(parseNivel(entity.getNivelUm()))
                .latitud(toDouble(entity.getLatitud()))
                .longitud(toDouble(entity.getLongitud()))
                .descripcion(nivelToDescripcion(entity.getNivelUm()))
                .telefono(entity.getTelefono())
                .sitioWeb(entity.getSitioWeb() != null ? entity.getSitioWeb() : "")
                .siglas(siglas)
                .direccion(entity.getDireccion())
                .provincia(provincia)
                .status(entity.getEstado())
                .createdBy(entity.getUsuCreacion())
                .createdAt(entity.getFecCreacion())
                .updatedBy(entity.getUsuActualizacion())
                .updatedAt(entity.getFecActualizacion())
                .build();
    }

    public DirUnidadMedTpEntity toEntity(UnidadMedica domain) {
        if (domain == null) {
            return null;
        }

        return DirUnidadMedTpEntity.builder()
                .nombre(domain.getNombre())
                .nivelUm(domain.getNivel() != null ? String.valueOf(domain.getNivel()) : null)
                .latitud(toBigDecimal(domain.getLatitud()))
                .longitud(toBigDecimal(domain.getLongitud()))
                .telefono(trunc(domain.getTelefono(), 10))
                .sitioWeb(trunc(domain.getSitioWeb(), 100))
                .siglas(trunc(domain.getSiglas(), 10))
                .direccion(trunc(domain.getDireccion(), 200))
                .nombreProvincia(domain.getProvincia())
                .estado(domain.getStatus() != null ? domain.getStatus() : "A")
                .usuCreacion(trunc(domain.getCreatedBy(), 10))
                .usuActualizacion(trunc(domain.getUpdatedBy(), 10))
                .fecCreacion(domain.getCreatedAt())
                .fecActualizacion(domain.getUpdatedAt())
                .build();
    }

    private Integer parseNivel(String nivelUm) {
        if (nivelUm == null || nivelUm.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(nivelUm.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String nivelToDescripcion(String nivelUm) {
        if (nivelUm == null || nivelUm.isBlank()) {
            return "";
        }
        return switch (nivelUm.trim()) {
            case "1" -> "I NIVEL";
            case "2" -> "II NIVEL";
            case "3" -> "III NIVEL";
            default -> "NIVEL " + nivelUm.trim();
        };
    }

    private Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    private BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    private String trunc(String value, int maxLen) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.length() > maxLen ? trimmed.substring(0, maxLen) : trimmed;
    }
}
