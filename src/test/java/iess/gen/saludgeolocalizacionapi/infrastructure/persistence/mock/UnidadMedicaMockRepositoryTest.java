/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.persistence.mock;

import iess.gen.saludgeolocalizacionapi.application.port.UnidadMedicaRepositoryPort;
import iess.gen.saludgeolocalizacionapi.model.UnidadMedica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("mock")
class UnidadMedicaMockRepositoryTest {

    @Autowired
    private UnidadMedicaRepositoryPort repository;

    @Test
    void findAllActive_shouldLoadJsonData() {
        List<UnidadMedica> unidades = repository.findAllActive();

        assertThat(unidades).isNotEmpty();
    }

    @Test
    void findBySiglas_shouldReturnMatch() {
        List<UnidadMedica> unidades = repository.findAllActive();
        String siglas = unidades.getFirst().getSiglas();

        assertThat(repository.findBySiglas(siglas)).isPresent();
        assertThat(repository.findBySiglas(" ")).isEmpty();
    }

    @Test
    void search_shouldFilterByTerminoProvinciaAndNivel() {
        UnidadMedica sample = repository.findAllActive().getFirst();

        List<UnidadMedica> byProvincia = repository.search(null, sample.getProvincia(), null);
        List<UnidadMedica> byNivel = repository.search(null, null, sample.getNivel());
        List<UnidadMedica> byTerm = repository.search(sample.getNombre(), null, null);

        assertThat(byProvincia).isNotEmpty();
        assertThat(byNivel).isNotEmpty();
        assertThat(byTerm).isNotEmpty();
    }

    @Test
    void save_shouldPersistInMemory() {
        UnidadMedica nueva = UnidadMedica.builder()
                .nombre("TEST-UNIDAD")
                .siglas("TEST-U")
                .nivel(1)
                .provincia("PICHINCHA")
                .status("A")
                .build();

        UnidadMedica saved = repository.save(nueva);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
