/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <b> Entidad de dominio pura para representar una Unidad Médica. </b>
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadMedica {

    /** Identificador único de la unidad médica. */
    private Long id;

    /** Nombre de la unidad médica. */
    private String nombre;

    /** Nivel de la unidad médica (1, 2, 3). */
    private Integer nivel;

    /** Latitud para geolocalización. */
    private Double latitud;

    /** Longitud para geolocalización. */
    private Double longitud;

    /** Descripción adicional de la unidad médica. */
    private String descripcion;

    /** Teléfono de contacto. */
    private String telefono;

    /** Sitio web oficial. */
    private String sitioWeb;

    /** Siglas identificadoras (ej: HCAM). */
    private String siglas;

    /** Dirección física. */
    private String direccion;

    /** Provincia donde se ubica la unidad médica. */
    private String provincia;

    // ── Auditoría obligatoria ──────────────────────
    /** Estado del registro (A=Activo, I=Inactivo, E=Eliminado). */
    private String status;

    /** Usuario que creó el registro. */
    private String createdBy;

    /** Fecha de creación. */
    private LocalDateTime createdAt;

    /** Usuario que realizó la última actualización. */
    private String updatedBy;

    /** Fecha de última actualización. */
    private LocalDateTime updatedAt;

    /** Usuario que realizó la eliminación lógica. */
    private String deletedBy;

    /** Fecha de eliminación lógica. */
    private LocalDateTime deletedAt;
}
