/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.oracle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA para DIRGEN_OWNER.DIR_UNIDADESMED_TP (Oracle DBDVP).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DIR_UNIDADESMED_TP")
@Profile("oracle")
public class DirUnidadMedTpEntity {

    @Id
    @Column(name = "ID_UNIDADMED", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_UM", length = 200)
    private String nombre;

    @Column(name = "CODDIVPOL", nullable = false, length = 12)
    private String coddivpol;

    @Column(name = "NOMBRE_PROVINCIA", length = 100)
    private String nombreProvincia;

    @Column(name = "NOMBRE_CANTON", length = 100)
    private String nombreCanton;

    @Column(name = "NIVEL_UM", length = 1)
    private String nivelUm;

    @Column(name = "SIGLAS_UM", length = 10)
    private String siglas;

    @Column(name = "DIRECCION_UM", length = 200)
    private String direccion;

    @Column(name = "TELEFONO_UM", length = 10)
    private String telefono;

    @Column(name = "LATITUD_UM", precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(name = "LONGITUD_UM", precision = 10, scale = 7)
    private BigDecimal longitud;

    @Column(name = "SITIOWEB_UM", length = 100)
    private String sitioWeb;

    @Column(name = "ESTADO", length = 1)
    private String estado;

    @Column(name = "USU_CREACION", length = 10)
    private String usuCreacion;

    @Column(name = "USU_ACTUALIZACION", length = 10)
    private String usuActualizacion;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fecCreacion;

    @Column(name = "FEC_ACTUALIZACION")
    private LocalDateTime fecActualizacion;

    @Column(name = "IP_EQUIPO", length = 40)
    private String ipEquipo;
}
