/**
 * Copyright 2026 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */
package iess.gen.basespringapi.infrastructure.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB para auditoría — solo cuando MONGO_ENABLED=true (PAS-EST-055 §17).
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "MONGO_ENABLED", havingValue = "true")
@EnableMongoRepositories(basePackages = "iess.gen.basespringapi.infrastructure.persistence.mongo")
public class MongoConfig {

    private static final String AUDITORIA_DB = "AUDITORIA_IESS";

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), AUDITORIA_DB);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate template = new MongoTemplate(mongoDatabaseFactory());
        try {
            template.getDb().runCommand(new org.bson.Document("ping", 1));
            log.info("MongoDB: conexión exitosa a '{}'.", AUDITORIA_DB);
        } catch (Exception e) {
            log.warn("MongoDB: no se pudo conectar a '{}'. Error: {}", AUDITORIA_DB, e.getMessage());
        }
        return template;
    }
}
