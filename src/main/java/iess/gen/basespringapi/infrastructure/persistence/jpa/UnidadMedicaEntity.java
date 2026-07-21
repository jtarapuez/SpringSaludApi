/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <b> Entidad JPA para representar la persistencia de una Unidad Médica. </b>
 * (Versión inicial sin anotaciones de JPA para permitir funcionamiento Mocked).
 *
 * @author Juan Carlos Estévez Hidalgo
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Carlos Estévez Hidalgo , Date: 18 jun 2026]
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadMedicaEntity {

    private UUID id;
    private String nombre;
    private Integer nivel;
    private Double latitud;
    private Double longitud;
    private String descripcion;
    private String telefono;
    private String sitioWeb;
    private String siglas;
    private String direccion;
    private String provincia;

    // Campos de Auditoría
    @Builder.Default
    private String status = "A";
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
