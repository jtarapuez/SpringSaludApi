/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Campos comunes de unidad médica en DTOs REST (mapa / request / response).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class UnidadMedicaCamposDto {

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
}
