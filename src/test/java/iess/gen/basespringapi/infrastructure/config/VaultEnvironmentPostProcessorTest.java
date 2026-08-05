/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Cobertura unitaria de {@link VaultEnvironmentPostProcessor} (Sonar New Code).
 */
class VaultEnvironmentPostProcessorTest {

    private final VaultEnvironmentPostProcessor processor = new VaultEnvironmentPostProcessor();
    private final SpringApplication application = new SpringApplication();

    @Test
    void whenVaultDisabled_skipsLoading() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("VAULT_ENABLED", "false");

        processor.postProcessEnvironment(environment, application);

        assertThat(environment.getPropertySources().contains("vault-secrets")).isFalse();
    }

    @Test
    void whenVaultEnabled_andVaultUnreachable_doesNotFail() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("VAULT_ENABLED", "true");
        environment.setProperty("VAULT_HOST", "127.0.0.1");
        environment.setProperty("VAULT_PORT", "1");
        environment.setProperty("VAULT_SCHEME", "http");
        environment.setProperty("VAULT_TOKEN", "test-token");
        environment.setProperty("DB_ENGINE", "oracle");

        assertThatCode(() -> processor.postProcessEnvironment(environment, application))
                .doesNotThrowAnyException();

        // leerSecreto captura errores; el processor aún puede registrar defaults en vault-secrets
        assertThat(environment.getProperty("spring.datasource.url"))
                .isEqualTo("jdbc:oracle:thin:@localhost:1521/DBDVP");
    }
}