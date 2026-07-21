/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.controller;

import iess.gen.basespringapi.application.usecase.UnidadMedicaUseCase;
import iess.gen.basespringapi.infrastructure.controller.dto.ProvinciaUnidadesPublicResponse;
import iess.gen.basespringapi.infrastructure.controller.dto.UnidadMedicaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gestionar los endpoints de Unidades Médicas.
 */
@Slf4j
@RestController
@RequestMapping("/unidades-medicas")
@RequiredArgsConstructor
@Tag(name = "Unidades Médicas", description = "Consulta de unidades médicas IESS para el mapa de geolocalización")
public class UnidadMedicaController {

    private final UnidadMedicaUseCase useCase;

    @GetMapping
    @Operation(
            summary = "Listar unidades médicas agrupadas por provincia",
            description = "Retorna todas las unidades activas. Acepta filtros opcionales por provincia, nivel o texto."
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public ResponseEntity<List<ProvinciaUnidadesPublicResponse>> listarAgrupadas(
            @Parameter(description = "Filtrar por provincia, ej: PICHINCHA")
            @RequestParam(required = false) String provincia,
            @Parameter(description = "Filtrar por nivel de atención (1, 2 o 3)")
            @RequestParam(required = false) Integer nivel,
            @Parameter(description = "Buscar por nombre, siglas o dirección")
            @RequestParam(required = false) String q) {

        if ((provincia != null && !provincia.isBlank()) || nivel != null || (q != null && !q.isBlank())) {
            log.info("REST Request - Filtrar unidades médicas provincia={}, nivel={}, q={}", provincia, nivel, q);
            return ResponseEntity.ok(useCase.buscarUnidades(q, provincia, nivel));
        }

        log.info("REST Request - Listar unidades médicas agrupadas por provincia");
        return ResponseEntity.ok(useCase.obtenerUnidadesAgrupadas());
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar unidades médicas por término",
            description = "Busca en nombre, siglas, dirección y provincia."
    )
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    public ResponseEntity<List<ProvinciaUnidadesPublicResponse>> buscar(
            @Parameter(description = "Término de búsqueda", required = true, example = "hospital")
            @RequestParam("q") String termino,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) Integer nivel) {
        log.info("REST Request - Buscar unidades médicas q={}, provincia={}, nivel={}", termino, provincia, nivel);
        return ResponseEntity.ok(useCase.buscarUnidades(termino, provincia, nivel));
    }

    @GetMapping("/siglas/{siglas}")
    @Operation(summary = "Obtener unidad médica por siglas", description = "Ejemplo: HCAM, HBEP")
    @ApiResponse(responseCode = "200", description = "Unidad encontrada")
    @ApiResponse(responseCode = "404", description = "Siglas no encontradas")
    public ResponseEntity<UnidadMedicaResponse> buscarPorSiglas(
            @Parameter(description = "Siglas de la unidad", example = "HCAM")
            @PathVariable String siglas) {
        log.info("REST Request - Buscar unidad médica por siglas: {}", siglas);
        return ResponseEntity.ok(useCase.buscarPorSiglas(siglas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener unidad médica por ID")
    @ApiResponse(responseCode = "200", description = "Unidad encontrada")
    @ApiResponse(responseCode = "404", description = "ID no encontrado")
    public ResponseEntity<UnidadMedicaResponse> buscarPorId(
            @Parameter(description = "UUID de la unidad")
            @PathVariable UUID id) {
        log.info("REST Request - Buscar unidad médica por ID: {}", id);
        return ResponseEntity.ok(useCase.buscarPorId(id));
    }
}
