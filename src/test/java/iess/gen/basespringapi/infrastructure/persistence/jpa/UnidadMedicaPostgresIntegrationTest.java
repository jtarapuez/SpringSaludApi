/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.persistence.jpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Pruebas de integración contra PostgreSQL local (iess_salud).
 * Se ejecutan solo si RUN_POSTGRES_IT=true y la BD está disponible.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("postgres")
@EnabledIfEnvironmentVariable(named = "RUN_POSTGRES_IT", matches = "true")
class UnidadMedicaPostgresIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarUnidadesMedicas_shouldReadFromDatabase() throws Exception {
        mockMvc.perform(get("/unidades-medicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$[0].provincia").exists())
                .andExpect(jsonPath("$[0].unidades[0].siglas").exists());
    }

    @Test
    void buscarPorSiglasHcam_shouldReturnHospital() throws Exception {
        mockMvc.perform(get("/unidades-medicas/siglas/HCAM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.siglas").value("HCAM"))
                .andExpect(jsonPath("$.nombre").value(containsString("CARLOS ANDRADE")));
    }

    @Test
    void filtrarPorProvincia_shouldReturnPichinchaUnits() throws Exception {
        mockMvc.perform(get("/unidades-medicas")
                        .param("provincia", "PICHINCHA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].provincia").value("PICHINCHA"))
                .andExpect(jsonPath("$[0].unidades.length()").value(greaterThan(0)));
    }
}
