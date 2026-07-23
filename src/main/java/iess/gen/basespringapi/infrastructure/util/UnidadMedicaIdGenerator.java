/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Genera identificadores estables para unidades médicas a partir de sus siglas.
 *
 * @author Juan Pablo Tarapuez
 * @version Revision: 1.0
 * <p>
 * [Author: Juan Pablo Tarapuez , Date: 18 jun 2026]
 * </p>
 */
public final class UnidadMedicaIdGenerator {

    private static final String NAMESPACE = "iess-unidad-medica-";

    private UnidadMedicaIdGenerator() {
    }

    public static UUID fromSiglas(String siglas, String provincia, String nombre) {
        String siglasKey = (siglas != null && !siglas.isBlank())
                ? siglas.trim().toUpperCase()
                : "SIN-SIGLAS";
        String provinciaKey = (provincia != null && !provincia.isBlank())
                ? provincia.trim().toUpperCase()
                : "SIN-PROVINCIA";
        String nombreKey = (siglas == null || siglas.isBlank()) && nombre != null
                ? nombre.trim().toUpperCase()
                : "";

        return UUID.nameUUIDFromBytes(
                (NAMESPACE + provinciaKey + "-" + siglasKey + "-" + nombreKey).getBytes(StandardCharsets.UTF_8)
        );
    }
}
