/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */

package iess.gen.saludgeolocalizacionapi.infrastructure.persistence.oracle;

import iess.gen.saludgeolocalizacionapi.infrastructure.mapper.DirUnidadMedTpMapper;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnidadMedicaOracleRepositoryTest {

    @Mock
    private DirUnidadMedTpJpaRepository jpaRepository;

    private UnidadMedicaOracleRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UnidadMedicaOracleRepository(jpaRepository, new DirUnidadMedTpMapper());
    }

    @Test
    void findAllActive_shouldMapEntities() {
        when(jpaRepository.findAllActive()).thenReturn(List.of(sampleEntity(1L, "HCAM", "PICHINCHA")));

        List<UnidadMedica> result = repository.findAllActive();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSiglas()).isEqualTo("HCAM");
    }

    @Test
    void findById_shouldReturnEmptyWhenNull() {
        assertThat(repository.findById(null)).isEmpty();
    }

    @Test
    void findById_shouldMapWhenFound() {
        when(jpaRepository.findActiveById(5L)).thenReturn(Optional.of(sampleEntity(5L, "HT", "GUAYAS")));

        Optional<UnidadMedica> result = repository.findById(5L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(5L);
    }

    @Test
    void findBySiglas_shouldReturnEmptyForBlank() {
        assertThat(repository.findBySiglas(" ")).isEmpty();
    }

    @Test
    void findBySiglas_shouldMapFirstMatch() {
        when(jpaRepository.findActiveBySiglas("hcam"))
                .thenReturn(List.of(sampleEntity(1L, "HCAM", "PICHINCHA")));

        Optional<UnidadMedica> result = repository.findBySiglas("hcam");

        assertThat(result).isPresent();
        assertThat(result.get().getSiglas()).isEqualTo("HCAM");
    }

    @Test
    void search_shouldFilterByProvince() {
        when(jpaRepository.searchActive(eq("hospital"), eq(null), eq(null), eq(null)))
                .thenReturn(List.of(
                        sampleEntity(1L, "HCAM", "PICHINCHA"),
                        sampleEntity(2L, "HGY", "GUAYAS")
                ));

        List<UnidadMedica> result = repository.search("hospital", "PICHINCHA", null);

        assertThat(result).extracting(UnidadMedica::getProvincia).containsExactly("PICHINCHA");
    }

    @Test
    void save_shouldPersistAndReturnDomain() {
        UnidadMedica input = UnidadMedica.builder()
                .nombre("Nueva Unidad")
                .siglas("NU")
                .nivel(1)
                .provincia("AZUAY")
                .build();

        when(jpaRepository.save(any(DirUnidadMedTpEntity.class)))
                .thenAnswer(invocation -> {
                    DirUnidadMedTpEntity saved = invocation.getArgument(0);
                    saved.setId(99L);
                    return saved;
                });

        UnidadMedica saved = repository.save(input);

        assertThat(saved.getId()).isEqualTo(99L);
        assertThat(saved.getSiglas()).isEqualTo("NU");
        verify(jpaRepository).save(any(DirUnidadMedTpEntity.class));
    }

    private DirUnidadMedTpEntity sampleEntity(Long id, String siglas, String provincia) {
        return DirUnidadMedTpEntity.builder()
                .id(id)
                .nombre("Unidad " + siglas)
                .siglas(siglas)
                .nombreProvincia(provincia)
                .nivelUm("2")
                .estado("A")
                .latitud(BigDecimal.valueOf(-0.1))
                .longitud(BigDecimal.valueOf(-78.4))
                .build();
    }
}
