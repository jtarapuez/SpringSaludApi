# ============================================================
#  BaseSpringApi - Dockerfile
#  PAS-EST-055 Etapa 2
#
#  Multi-stage build (sin mvnw — usa Maven del contenedor)
# ============================================================

FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src/ src/
RUN mvn package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine AS runtime

LABEL application="BaseSpringApi"
LABEL version="1.0.0"

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

RUN mkdir -p uploads && chown appuser:appgroup uploads

COPY --from=builder /app/target/BaseSpringApi-1.0.0.jar app.jar

USER appuser

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -XX:+UseG1GC \
               -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
