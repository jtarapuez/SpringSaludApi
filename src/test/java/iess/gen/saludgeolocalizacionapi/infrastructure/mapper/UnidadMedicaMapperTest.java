/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.mapper;

import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.ProvinciaUnidadesPublicResponse;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaPublicResponse;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaRequest;
import iess.gen.saludgeolocalizacionapi.infrastructure.controller.dto.UnidadMedicaResponse;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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
    void toDomain_shouldMapRequest() {
        UnidadMedicaRequest request = UnidadMedicaRequest.builder()
                .nombre("Hospital Test")
                .siglas("HT")
                .nivel(1)
                .latitud(-0.1)
                .longitud(-78.4)
                .descripcion("I NIVEL")
                .telefono("02-0000000")
                .sitioWeb("https://example.iess.gob.ec/")
                .direccion("Calle Test")
                .build();

        UnidadMedica domain = mapper.toDomain(request);

        assertThat(domain.getNombre()).isEqualTo("Hospital Test");
        assertThat(domain.getSiglas()).isEqualTo("HT");
        assertThat(domain.getNivel()).isEqualTo(1);
        assertThat(domain.getStatus()).isEqualTo("A");
        assertThat(domain.getCreatedAt()).isNotNull();
    }

    @Test
    void toResponse_shouldMapDomain() {
        Long id = 42L;
        UnidadMedica domain = UnidadMedica.builder()
                .id(id)
                .nombre("HOSPITAL DE ESPECIALIDADES - CARLOS ANDRADE MARÍN")
                .siglas("HCAM")
                .nivel(2)
                .latitud(-0.2051303)
                .longitud(-78.5048297)
                .status("A")
                .createdBy("system")
                .createdAt(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build();

        UnidadMedicaResponse response = mapper.toResponse(domain);

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getSiglas()).isEqualTo("HCAM");
        assertThat(response.getLatitud()).isEqualTo(-0.2051303);
        assertThat(response.getStatus()).isEqualTo("A");
    }

    @Test
    void toPublicResponse_shouldMapDomainWithoutAudit() {
        UnidadMedica domain = UnidadMedica.builder()
                .id(1L)
                .nombre("Hospital Test")
                .siglas("HT")
                .nivel(1)
                .latitud(-0.1)
                .longitud(-78.4)
                .status("A")
                .build();

        UnidadMedicaPublicResponse response = mapper.toPublicResponse(domain);

        assertThat(response.getNombre()).isEqualTo("Hospital Test");
        assertThat(response.getSiglas()).isEqualTo("HT");
        assertThat(response.getNivel()).isEqualTo(1);
    }

    @Test
    void toPublicResponseList_shouldGroupByProvince() {
        List<ProvinciaUnidadesPublicResponse> result = mapper.toPublicResponseList(List.of(
                sampleUnidad("HCAM", "PICHINCHA"),
                sampleUnidad("HGSF", "PICHINCHA"),
                sampleUnidad("HETMC", "GUAYAS")
        ));

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProvincia()).isEqualTo("GUAYAS");
        assertThat(result.get(1).getProvincia()).isEqualTo("PICHINCHA");
        assertThat(result.get(1).getUnidades()).hasSize(2);
        assertThat(result.get(1).getUnidades().get(0).getSiglas()).isEqualTo("HCAM");
    }

    @Test
    void toPublicResponseList_shouldReturnEmptyWhenNull() {
        assertThat(mapper.toPublicResponseList(null)).isEmpty();
    }

    private UnidadMedica sampleUnidad(String siglas, String provincia) {
        return UnidadMedica.builder()
                .siglas(siglas)
                .nombre("Unidad " + siglas)
                .provincia(provincia)
                .nivel(2)
                .build();
    }
}
