/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <b> DTO para retornar la información detallada de una Unidad Médica. </b>
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
public class UnidadMedicaResponse {

    private UUID id;
    private String nombre;
    private Integer nivel;
    private Double latitud;
    private Double longitud;
    private String descripcion;
    private String telefono;

    @JsonProperty("sitio_web")
    private String sitioWeb;

    private String siglas;
    private String direccion;

    // ── Campos de Auditoría ────────────────────────
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
