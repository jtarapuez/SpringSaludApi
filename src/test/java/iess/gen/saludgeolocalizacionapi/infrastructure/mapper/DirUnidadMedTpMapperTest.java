/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.mapper;

import iess.gen.saludgeolocalizacionapi.infrastructure.persistence.oracle.DirUnidadMedTpEntity;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DirUnidadMedTpMapperTest {

    private final DirUnidadMedTpMapper mapper = new DirUnidadMedTpMapper();

    @Test
    void toDomain_shouldMapEntityFields() {
        LocalDateTime now = LocalDateTime.now();
        DirUnidadMedTpEntity entity = DirUnidadMedTpEntity.builder()
                .id(1L)
                .nombre("Hospital Test")
                .nombreProvincia("PICHINCHA")
                .nivelUm("2")
                .siglas("HT")
                .direccion("Av. Test")
                .telefono("022222222")
                .latitud(BigDecimal.valueOf(-0.2))
                .longitud(BigDecimal.valueOf(-78.5))
                .sitioWeb("https://iess.gob.ec")
                .estado("A")
                .usuCreacion("tester")
                .usuActualizacion("tester2")
                .fecCreacion(now)
                .fecActualizacion(now)
                .build();

        UnidadMedica domain = mapper.toDomain(entity);

        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getNombre()).isEqualTo("Hospital Test");
        assertThat(domain.getProvincia()).isEqualTo("PICHINCHA");
        assertThat(domain.getNivel()).isEqualTo(2);
        assertThat(domain.getDescripcion()).isEqualTo("II NIVEL");
        assertThat(domain.getSiglas()).isEqualTo("HT");
        assertThat(domain.getLatitud()).isEqualTo(-0.2);
        assertThat(domain.getLongitud()).isEqualTo(-78.5);
    }

    @Test
    void toDomain_shouldHandleNullAndInvalidNivel() {
        assertThat(mapper.toDomain(null)).isNull();

        UnidadMedica domain = mapper.toDomain(DirUnidadMedTpEntity.builder()
                .id(2L)
                .nombre("Centro")
                .nivelUm("X")
                .build());

        assertThat(domain.getNivel()).isNull();
        assertThat(domain.getDescripcion()).isEqualTo("NIVEL X");
    }

    @Test
    void toEntity_shouldMapDomainAndTruncateFields() {
        UnidadMedica domain = UnidadMedica.builder()
                .id(3L)
                .nombre("Unidad Larga")
                .nivel(1)
                .latitud(-1.0)
                .longitud(-79.0)
                .telefono("0999999999")
                .sitioWeb("https://example.com/very-long-url")
                .siglas("SIGLALARGA")
                .direccion("D".repeat(250))
                .provincia("GUAYAS")
                .status("A")
                .createdBy("creator")
                .updatedBy("updater")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        DirUnidadMedTpEntity entity = mapper.toEntity(domain);

        assertThat(entity.getId()).isEqualTo(3L);
        assertThat(entity.getNivelUm()).isEqualTo("1");
        assertThat(entity.getTelefono()).hasSizeLessThanOrEqualTo(10);
        assertThat(entity.getSiglas()).hasSizeLessThanOrEqualTo(10);
        assertThat(entity.getDireccion()).hasSizeLessThanOrEqualTo(200);
        assertThat(entity.getEstado()).isEqualTo("A");
    }

    @Test
    void toEntity_shouldReturnNullForNullDomain() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
