/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.mapper;

import iess.gen.basespringapi.infrastructure.persistence.jpa.ProvinciaEntity;
import iess.gen.basespringapi.infrastructure.persistence.jpa.UnidadMedicaEntity;
import iess.gen.basespringapi.model.UnidadMedica;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
class UnidadMedicaMapperTest {

    private final UnidadMedicaMapper mapper = new UnidadMedicaMapper();

    @Test
    void toDomain_shouldMapEntityWithProvincia() {
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .id(1)
                .nomProvincia("PICHINCHA")
                .build();

        Long id = 42L;
        UnidadMedicaEntity entity = UnidadMedicaEntity.builder()
                .id(id)
                .provincia(provincia)
                .nombre("HOSPITAL DE ESPECIALIDADES - CARLOS ANDRADE MARÍN")
                .siglas("HCAM")
                .nivel(2)
                .descripcion("III NIVEL")
                .latitud(new BigDecimal("-0.2051303"))
                .longitud(new BigDecimal("-78.5048297"))
                .telefono("02-2564939")
                .sitioWeb("https://hcam.iess.gob.ec/")
                .direccion("18 de Septiembre N19-63")
                .estRegistro("A")
                .usuCreacion("system")
                .fecCreacion(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build();

        UnidadMedica domain = mapper.toDomain(entity);

        assertThat(domain.getId()).isEqualTo(id);
        assertThat(domain.getProvincia()).isEqualTo("PICHINCHA");
        assertThat(domain.getSiglas()).isEqualTo("HCAM");
        assertThat(domain.getLatitud()).isEqualTo(-0.2051303);
        assertThat(domain.getLongitud()).isEqualTo(-78.5048297);
        assertThat(domain.getStatus()).isEqualTo("A");
    }

    @Test
    void toEntity_shouldMapDomainWithProvincia() {
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .id(1)
                .nomProvincia("PICHINCHA")
                .build();

        UnidadMedica domain = UnidadMedica.builder()
                .nombre("Hospital Test")
                .siglas("HT")
                .nivel(1)
                .latitud(-0.1)
                .longitud(-78.4)
                .status("A")
                .build();

        UnidadMedicaEntity entity = mapper.toEntity(domain, provincia);

        assertThat(entity.getProvincia().getNomProvincia()).isEqualTo("PICHINCHA");
        assertThat(entity.getNombre()).isEqualTo("Hospital Test");
        assertThat(entity.getLatitud()).isEqualByComparingTo("-0.1");
        assertThat(entity.getEstRegistro()).isEqualTo("A");
    }
}
