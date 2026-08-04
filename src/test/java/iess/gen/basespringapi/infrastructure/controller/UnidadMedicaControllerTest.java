/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.controller;

import iess.gen.basespringapi.application.dto.ProvinciaUnidadesAgrupada;
import iess.gen.basespringapi.application.usecase.UnidadMedicaUseCase;
import iess.gen.basespringapi.infrastructure.mapper.UnidadMedicaMapper;
import iess.gen.basespringapi.model.UnidadMedica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@WebMvcTest(controllers = {UnidadMedicaController.class, HealthController.class})
@Import(UnidadMedicaMapper.class)
@ActiveProfiles("mock")
class UnidadMedicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnidadMedicaUseCase useCase;

    @Test
    void health_shouldReturnUp() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void listarAgrupadas_shouldReturnProvincias() throws Exception {
        when(useCase.obtenerUnidadesAgrupadas()).thenReturn(List.of(
                ProvinciaUnidadesAgrupada.builder()
                        .provincia("PICHINCHA")
                        .unidades(List.of(
                                UnidadMedica.builder()
                                        .siglas("HCAM")
                                        .nombre("Hospital Carlos Andrade Marín")
                                        .nivel(2)
                                        .latitud(-0.2051303)
                                        .longitud(-78.5048297)
                                        .build()
                        ))
                        .build()
        ));

        mockMvc.perform(get("/unidades-medicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].provincia").value("PICHINCHA"))
                .andExpect(jsonPath("$[0].unidades[0].siglas").value("HCAM"));
    }

    @Test
    void buscar_shouldApplyFilters() throws Exception {
        when(useCase.buscarUnidades(eq("hospital"), eq("PICHINCHA"), eq(2)))
                .thenReturn(List.of());

        mockMvc.perform(get("/unidades-medicas/buscar")
                        .param("q", "hospital")
                        .param("provincia", "PICHINCHA")
                        .param("nivel", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorSiglas_shouldReturnUnidad() throws Exception {
        when(useCase.buscarPorSiglas("HCAM")).thenReturn(
                UnidadMedica.builder()
                        .id(1L)
                        .siglas("HCAM")
                        .nombre("Hospital Carlos Andrade Marín")
                        .nivel(2)
                        .build()
        );

        mockMvc.perform(get("/unidades-medicas/siglas/HCAM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siglas").value("HCAM"));
    }

    @Test
    void buscarPorId_shouldReturnUnidad() throws Exception {
        Long id = 1L;
        when(useCase.buscarPorId(id)).thenReturn(
                UnidadMedica.builder()
                        .id(id)
                        .siglas("HCAM")
                        .build()
        );

        mockMvc.perform(get("/unidades-medicas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siglas").value("HCAM"));
    }
}
