/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Imprime en el log un resumen de servicios al completar el arranque (PAS-EST-055 Anexo 3).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupLogger {

    private final Environment environment;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Value("${DB_ENGINE:oracle}")
    private String dbEngine;

    @Value("${DB_ORACLE_HOST:}")
    private String oracleHost;

    @Value("${DB_ORACLE_PORT:1521}")
    private String oraclePort;

    @Value("${DB_ORACLE_SERVICE:}")
    private String oracleService;

    @Value("${app.data.json-path:classpath:data/unidades-medicas.json}")
    private String mockJsonPath;

    @Value("${VAULT_ENABLED:false}")
    private boolean vaultEnabled;

    @Value("${VAULT_HOST:localhost}")
    private String vaultHost;

    @Value("${VAULT_PORT:8200}")
    private String vaultPort;

    @Value("${MONGO_ENABLED:false}")
    private boolean mongoEnabled;

    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerPath;

    @EventListener(ApplicationReadyEvent.class)
    public void logResumenArranque() {
        String sep = "----------------------------------------------------------";
        String swaggerUrl = "http://localhost:" + serverPort + contextPath + swaggerPath;

        log.info("\n{}\n" +
                        "  BaseSpringApi - IESS Salud | Arranque completado\n" +
                        "{}\n" +
                        "  {}\n" +
                        "  Vault        : {}\n" +
                        "  MongoDB      : {}\n" +
                        "{}\n" +
                        "  Swagger UI    : {}\n" +
                        "  Health        : http://localhost:{}{}/health\n" +
                        "{}",
                sep, sep,
                resolverLineaDb(),
                resolverLineaVault(),
                mongoEnabled ? "Habilitado (Etapa 2)" : "Deshabilitado",
                sep,
                swaggerUrl,
                serverPort, contextPath,
                sep
        );
    }

    private String resolverLineaDb() {
        String perfil = perfilActivo();
        if ("mock".equalsIgnoreCase(perfil)) {
            return "BD Relacional : MOCK      | JSON en memoria | " + mockJsonPath;
        }
        String host = oracleHost != null && !oracleHost.isBlank() ? oracleHost : "(configurar DB_ORACLE_HOST)";
        String service = oracleService != null && !oracleService.isBlank() ? oracleService : "DBDVP";
        String detalle = host + ":" + oraclePort + "/" + service;
        return "BD Relacional : ORACLE    | " + tipoHost(host) + "  | " + detalle;
    }

    private String perfilActivo() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length > 0) {
            return profiles[0];
        }
        return dbEngine;
    }

    private String resolverLineaVault() {
        if (!vaultEnabled) {
            return "Deshabilitado (.env)";
        }
        return (esLocal(vaultHost) ? "Habilitado/Interno" : "Habilitado/Externo")
                + "  | " + vaultHost + ":" + vaultPort + "/BaseSpringApi/";
    }

    private String tipoHost(String host) {
        return esLocal(host) ? "Habilitado/Interno" : "Habilitado/Externo";
    }

    private boolean esLocal(String host) {
        return host != null && (host.equals("localhost") || host.equals("127.0.0.1"));
    }
}
