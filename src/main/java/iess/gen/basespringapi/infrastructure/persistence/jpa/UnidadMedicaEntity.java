/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA para unidades médicas (esquema salud).
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
@Entity
@Table(name = "unidades_medicas", schema = "salud")
@Profile("postgres")
public class UnidadMedicaEntity {

    @Id
    @Column(name = "id_unidad", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_provincia", nullable = false)
    private ProvinciaEntity provincia;

    @Column(name = "nom_unidad", nullable = false, length = 500)
    private String nombre;

    @Column(name = "siglas", nullable = false, length = 20)
    private String siglas;

    @JdbcTypeCode(SqlTypes.SMALLINT)
    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "latitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(name = "longitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitud;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "sitio_web", length = 500)
    private String sitioWeb;

    @Column(name = "direccion", length = 500)
    private String direccion;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "est_registro", nullable = false, length = 1)
    @Builder.Default
    private String estRegistro = "A";

    @Column(name = "usu_creacion", nullable = false, length = 50)
    @Builder.Default
    private String usuCreacion = "system";

    @Column(name = "fec_creacion", nullable = false)
    @Builder.Default
    private LocalDateTime fecCreacion = LocalDateTime.now();

    @Column(name = "ip_equipo", length = 45)
    private String ipEquipo;

    @Column(name = "usu_actualizacion", length = 50)
    private String usuActualizacion;

    @Column(name = "fec_actualizacion")
    private LocalDateTime fecActualizacion;

    @Column(name = "usu_eliminacion", length = 50)
    private String usuEliminacion;

    @Column(name = "fec_eliminacion")
    private LocalDateTime fecEliminacion;
}
