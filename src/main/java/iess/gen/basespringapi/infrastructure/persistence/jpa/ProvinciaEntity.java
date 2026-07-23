/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * Entidad JPA para el catálogo de provincias (esquema salud).
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
@Table(name = "provincias", schema = "salud")
public class ProvinciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Integer id;

    @Column(name = "nom_provincia", nullable = false, length = 100)
    private String nomProvincia;

    @Column(name = "cod_provincia", length = 10)
    private String codProvincia;

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
}
