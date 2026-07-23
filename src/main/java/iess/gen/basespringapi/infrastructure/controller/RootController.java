/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Endpoint raíz de la API con información de los recursos disponibles.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("application", "BaseSpringApi - IESS Salud");
        info.put("version", "1.0.0");
        info.put("status", "UP");
        info.put("endpoints", Map.of(
                "health", "/api/health",
                "unidades_medicas", "/api/unidades-medicas",
                "buscar", "/api/unidades-medicas/buscar?q={termino}",
                "por_siglas", "/api/unidades-medicas/siglas/{siglas}",
                "swagger_ui", "/api/swagger-ui/index.html"
        ));
        return ResponseEntity.ok(info);
    }
}
