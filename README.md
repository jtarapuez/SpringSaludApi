# SpringSaludApi

API REST Spring Boot para el proyecto de salud IESS: unidades médicas, geolocalización y consultas públicas.

## Stack

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- PostgreSQL
- SpringDoc OpenAPI (Swagger)

## Perfiles

| Perfil | Uso |
|--------|-----|
| `postgres` | **Por defecto.** Conexión a PostgreSQL `iess_salud` (esquema `salud`) |
| `mock` | Datos en memoria desde JSON (desarrollo sin BD) |

## Ejecución

Con PostgreSQL (recomendado):

```bash
mvn spring-boot:run
```

Solo con JSON en memoria:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mock
```

Variables opcionales: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`.

## Endpoints principales

- `GET /api/health`
- `GET /api/unidades-medicas`
- `GET /api/unidades-medicas?provincia=PICHINCHA&nivel=2&q=hospital`
- `GET /api/unidades-medicas/buscar?q=`
- `GET /api/unidades-medicas/siglas/{siglas}`
- `GET /api/unidades-medicas/{id}`

Swagger UI: `http://localhost:8080/api/swagger-ui/index.html`

## Base de datos

Scripts en `src/main/resources/db/`:

1. `01_create_database.sql`
2. `02_schema.sql`
3. `03_seed_provincias.sql`
4. `04_seed_unidades_medicas.sql`

Conexión por defecto:

```
jdbc:postgresql://localhost:5432/iess_salud?currentSchema=salud
usuario: iess_salud_user
```

## Tests

Unitarios y de controlador (perfil mock):

```bash
mvn test
```

Integración con PostgreSQL local:

```bash
RUN_POSTGRES_IT=true mvn test -Dtest=UnidadMedicaPostgresIntegrationTest
```

## Arquitectura (PAS-EST-055)

```
Controller → UseCase → RepositoryPort
                           ├── UnidadMedicaPostgresRepository (postgres)
                           └── UnidadMedicaMockRepository (mock)
```
