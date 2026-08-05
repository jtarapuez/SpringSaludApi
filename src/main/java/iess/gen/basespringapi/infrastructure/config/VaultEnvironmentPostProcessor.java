/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Carga secretos desde HashiCorp Vault en la fase de bootstrap (PAS-EST-055).
 * Solo activo cuando VAULT_ENABLED=true.
 */
@Slf4j
public class VaultEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String LOCALHOST = "localhost";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String vaultEnabled = environment.getProperty("VAULT_ENABLED", "false");
        if (!"true".equalsIgnoreCase(vaultEnabled)) {
            return;
        }

        String vaultHost = environment.getProperty("VAULT_HOST", LOCALHOST);
        String vaultPort = environment.getProperty("VAULT_PORT", "8200");
        String vaultScheme = environment.getProperty("VAULT_SCHEME", "http");
        String vaultToken = environment.getProperty("VAULT_TOKEN", "root-token");
        String dbEngine = environment.getProperty("DB_ENGINE", "oracle");

        log.info("Vault: cargando secretos en fase de bootstrap...");

        try {
            VaultEndpoint endpoint = VaultEndpoint.from(
                    new URI(vaultScheme + "://" + vaultHost + ":" + vaultPort));

            VaultTemplate vaultTemplate = new VaultTemplate(
                    endpoint, new TokenAuthentication(vaultToken));

            Map<String, Object> secretos = new HashMap<>();

            if ("oracle".equalsIgnoreCase(dbEngine)) {
                leerSecreto(vaultTemplate, "BaseSpringApi/data/database/oracle",
                        Map.of(
                                "host", "DB_ORACLE_HOST",
                                "port", "DB_ORACLE_PORT",
                                KEY_USERNAME, "DB_ORACLE_USERNAME",
                                KEY_PASSWORD, "DB_ORACLE_PASSWORD",
                                "service", "DB_ORACLE_SERVICE"
                        ), secretos);

                String host = val(secretos, "DB_ORACLE_HOST", LOCALHOST);
                String port = val(secretos, "DB_ORACLE_PORT", "1521");
                String service = val(secretos, "DB_ORACLE_SERVICE", "DBDVP");
                String user = val(secretos, "DB_ORACLE_USERNAME", "DIRGEN_OWNER");
                String pass = val(secretos, "DB_ORACLE_PASSWORD", "");
                secretos.put("spring.datasource.url",
                        "jdbc:oracle:thin:@" + host + ":" + port + "/" + service);
                secretos.put("spring.datasource.username", user);
                secretos.put("spring.datasource.password", pass);
            }

            leerSecreto(vaultTemplate, "BaseSpringApi/data/database/mongo",
                    Map.of(
                            "host", "DB_MONGO_HOST",
                            "port", "DB_MONGO_PORT",
                            "bdd", "DB_MONGO_NAME",
                            KEY_USERNAME, "DB_MONGO_USERNAME",
                            KEY_PASSWORD, "DB_MONGO_PASSWORD",
                            "auth_db", "DB_MONGO_AUTH_DB"
                    ), secretos);

            String mongoHost = val(secretos, "DB_MONGO_HOST", LOCALHOST);
            String mongoPort = val(secretos, "DB_MONGO_PORT", "27017");
            String mongoUser = val(secretos, "DB_MONGO_USERNAME", "mongo_user");
            String mongoPass = val(secretos, "DB_MONGO_PASSWORD", "mongo_password");
            String mongoAuth = val(secretos, "DB_MONGO_AUTH_DB", "admin");
            secretos.put("spring.data.mongodb.uri",
                    "mongodb://" + mongoUser + ":" + mongoPass +
                            "@" + mongoHost + ":" + mongoPort +
                            "/AUDITORIA_IESS?authSource=" + mongoAuth);

            environment.getPropertySources().addFirst(
                    new MapPropertySource("vault-secrets", secretos));
            log.info("Vault: {} propiedades cargadas exitosamente.", secretos.size());

        } catch (Exception e) {
            log.warn("Vault: error al conectar. Usando valores del .env. Error: {}", e.getMessage());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void leerSecreto(VaultTemplate vaultTemplate, String path,
                             Map<String, String> mapaClave,
                             Map<String, Object> secretos) {
        try {
            VaultResponseSupport<Map> response = vaultTemplate.read(path, Map.class);
            if (response == null || response.getData() == null) {
                log.warn("Vault: path '{}' no encontrado.", path);
                return;
            }

            Object dataAnidado = response.getData().get("data");
            final Map<String, Object> valores = (dataAnidado instanceof Map)
                    ? (Map<String, Object>) dataAnidado
                    : response.getData();

            mapaClave.forEach((claveVault, propSistema) -> {
                Object valor = valores.get(claveVault);
                if (valor != null) {
                    secretos.put(propSistema, valor.toString());
                    log.info("Vault: [OK] {}/{} → {}", path, claveVault, propSistema);
                } else {
                    log.warn("Vault: clave '{}' no encontrada en '{}'.", claveVault, path);
                }
            });
        } catch (Exception e) {
            log.warn("Vault: error al leer '{}'. Error: {}", path, e.getMessage());
        }
    }

    private String val(Map<String, Object> secretos, String clave, String defecto) {
        Object v = secretos.get(clave);
        return (v != null && !v.toString().isBlank()) ? v.toString() : defecto;
    }
}
