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
| `mock` | Datos en memoria desde JSON (desarrollo sin BD) |
| `postgres` | Conexión a PostgreSQL local o servidor |

## Ejecución

```bash
mvn spring-boot:run
```

Con PostgreSQL:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

Variables opcionales: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`.

## Endpoints principales

- `GET /api/health`
- `GET /api/unidades-medicas`
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

Ver `src/main/resources/db/README_EJECUCION.txt` para el orden de ejecución.
