/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.util;

import java.text.Normalizer;
import java.util.Map;

/**
 * Normaliza nombres de provincias para mantener consistencia en filtros y agrupaciones.
 */
public final class ProvinciaNormalizer {

    private static final Map<String, String> ALIAS = Map.ofEntries(
            Map.entry("LOS RIOS", "LOS RÍOS"),
            Map.entry("EL ORO ", "EL ORO"),
            Map.entry("BOLIVAR", "BOLÍVAR"),
            Map.entry("CANAR", "CAÑAR"),
            Map.entry("MANABI", "MANABÍ"),
            Map.entry("COTOPAXI", "COTOPAXI"),
            Map.entry("SUCUMBIOS", "SUCUMBÍOS")
    );

    private ProvinciaNormalizer() {
    }

    public static String normalize(String provincia) {
        if (provincia == null || provincia.isBlank()) {
            return "DESCONOCIDA";
        }

        String trimmed = provincia.trim().toUpperCase();
        return ALIAS.getOrDefault(trimmed, trimmed);
    }

    public static boolean matches(String provincia, String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return true;
        }
        String normalizedProvincia = normalize(provincia);
        String normalizedFiltro = normalize(filtro);
        return normalizedProvincia.equals(normalizedFiltro)
                || stripAccents(normalizedProvincia).equals(stripAccents(normalizedFiltro));
    }

    private static String stripAccents(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
