/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.saludgeolocalizacionapi.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;

class ApplicationStartupLoggerTest {

    @Test
    void logStartupSummary_withMockProfile_shouldNotThrow() {
        ApplicationStartupLogger logger = createLogger(new MockEnvironment());
        assertThatCode(logger::logResumenArranque).doesNotThrowAnyException();
    }

    @Test
    void logStartupSummary_withOracleProfile_shouldNotThrow() {
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("oracle");

        ApplicationStartupLogger logger = createLogger(environment);
        ReflectionTestUtils.setField(logger, "dbEngine", "oracle");
        ReflectionTestUtils.setField(logger, "oracleHost", "192.168.29.66");
        ReflectionTestUtils.setField(logger, "oraclePort", "1521");
        ReflectionTestUtils.setField(logger, "oracleService", "DBDVP");

        assertThatCode(logger::logResumenArranque).doesNotThrowAnyException();
    }

    @Test
    void logStartupSummary_withVaultEnabled_shouldNotThrow() {
        ApplicationStartupLogger logger = createLogger(new MockEnvironment());
        ReflectionTestUtils.setField(logger, "vaultEnabled", true);
        ReflectionTestUtils.setField(logger, "vaultHost", "10.0.0.5");
        ReflectionTestUtils.setField(logger, "vaultPort", "8200");
        ReflectionTestUtils.setField(logger, "mongoEnabled", true);

        assertThatCode(logger::logResumenArranque).doesNotThrowAnyException();
    }

    @Test
    void logStartupSummary_withLocalVault_shouldNotThrow() {
        ApplicationStartupLogger logger = createLogger(new MockEnvironment());
        ReflectionTestUtils.setField(logger, "vaultEnabled", true);
        ReflectionTestUtils.setField(logger, "vaultHost", "localhost");

        assertThatCode(logger::logResumenArranque).doesNotThrowAnyException();
    }

    private ApplicationStartupLogger createLogger(MockEnvironment environment) {
        ApplicationStartupLogger logger = new ApplicationStartupLogger(environment);
        ReflectionTestUtils.setField(logger, "serverPort", "8080");
        ReflectionTestUtils.setField(logger, "contextPath", "/api");
        ReflectionTestUtils.setField(logger, "dbEngine", "mock");
        ReflectionTestUtils.setField(logger, "mockJsonPath", "classpath:data/unidades-medicas.json");
        ReflectionTestUtils.setField(logger, "swaggerPath", "/swagger-ui.html");
        return logger;
    }
}
