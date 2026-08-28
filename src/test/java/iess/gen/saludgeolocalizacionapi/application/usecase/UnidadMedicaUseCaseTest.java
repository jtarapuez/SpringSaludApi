/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.application.usecase;

import iess.gen.saludgeolocalizacionapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class UnidadMedicaUseCaseTest {

    @Mock
    private UnidadMedicaRepositoryPort repository;

    private UnidadMedicaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UnidadMedicaUseCase(repository);
    }

    @Test
    void obtenerUnidadesActivas_shouldReturnDomainList() {
        when(repository.findAllActive()).thenReturn(List.of(
                sampleUnidad("HCAM", "PICHINCHA"),
                sampleUnidad("HETMC", "GUAYAS")
        ));

        List<UnidadMedica> result = useCase.obtenerUnidadesActivas();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(UnidadMedica::getSiglas).containsExactly("HCAM", "HETMC");
    }

    @Test
    void buscarUnidades_shouldDelegateToRepository() {
        when(repository.search("hospital", "PICHINCHA", 2))
                .thenReturn(List.of(sampleUnidad("HCAM", "PICHINCHA")));

        List<UnidadMedica> result = useCase.buscarUnidades("hospital", "PICHINCHA", 2);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSiglas()).isEqualTo("HCAM");
    }

    @Test
    void buscarPorSiglas_shouldReturnDomainEntity() {
        UnidadMedica unidad = sampleUnidad("HCAM", "PICHINCHA");
        when(repository.findBySiglas("HCAM")).thenReturn(Optional.of(unidad));

        UnidadMedica result = useCase.buscarPorSiglas("HCAM");

        assertThat(result.getSiglas()).isEqualTo("HCAM");
        assertThat(result.getNombre()).isEqualTo("Unidad HCAM");
    }

    @Test
    void buscarPorId_shouldThrowWhenNotFound() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.buscarPorId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(id.toString());
    }

    private UnidadMedica sampleUnidad(String siglas, String provincia) {
        return UnidadMedica.builder()
                .id(1L)
                .siglas(siglas)
                .nombre("Unidad " + siglas)
                .nivel(2)
                .latitud(-0.2)
                .longitud(-78.5)
                .provincia(provincia)
                .status("A")
                .build();
    }
}
