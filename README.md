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
| `oracle` | **Por defecto.** Conexión a Oracle DBDVP `DIRGEN_OWNER` (`DIR_UNIDADESMED_TP`) |
| `postgres` | PostgreSQL `iess_salud` (esquema `salud`) |
| `mock` | Datos en memoria desde JSON (desarrollo sin BD) |

## Configuración del entorno (PAS-EST-055)

### Etapa 1 — Variables `.env`

```bash
cp .env.example .env
# Completar DB_ORACLE_PASSWORD (o DB_POSTGRES_PASSWORD según motor)
```

| Variable | Descripción |
|----------|-------------|
| `DB_ENGINE` | `oracle` (default), `postgres` o `mock` |
| `DB_ORACLE_*` | Conexión Oracle DBDVP |
| `DB_POSTGRES_*` | Conexión PostgreSQL local |

### Etapa 2 — Vault y Docker

| Variable | Descripción |
|----------|-------------|
| `VAULT_ENABLED` | `true` para cargar secretos desde HashiCorp Vault |
| `VAULT_HOST`, `VAULT_PORT`, `VAULT_TOKEN` | Conexión Vault local (`root-token` en dev) |
| `MONGO_ENABLED` | `true` para auditoría Mongo (Etapa 3) |

**Utilitarios locales (PostgreSQL, Mongo, Vault, MinIO):**

```bash
docker-compose -f docker-compose-utilitarios.yml up -d
docker-compose -f docker-compose-utilitarios.yml up vault-init
```

**Arranque con Vault:**

```bash
# .env: VAULT_ENABLED=true, VAULT_TOKEN=root-token
mvn spring-boot:run
```

**App dockerizada (modo liviano — solo API, Oracle externo):**

```bash
# Validar puertos primero
.cursor/skills/lev-basespringapi-docker/scripts/check-ports.sh

# Levantar
docker-compose up -d --build
```

Documentación completa: [`Documentacion/DOCKER_BASESPRINGAPI.md`](../Documentacion/DOCKER_BASESPRINGAPI.md)  
Paso a calidad (Vault/DNTSI): [`Documentacion/PASO_A_CALIDAD_VAULT_DNTSI.md`](../Documentacion/PASO_A_CALIDAD_VAULT_DNTSI.md)  
Skill de arranque: `.cursor/skills/lev-basespringapi-docker/`

## Ejecución

Con Oracle DBDVP (recomendado):

```bash
# .env con DB_ENGINE=oracle y DB_ORACLE_PASSWORD=...
mvn spring-boot:run
```

Con PostgreSQL local:

```bash
DB_ENGINE=postgres mvn spring-boot:run
```

Solo con JSON en memoria:

```bash
DB_ENGINE=mock mvn spring-boot:run
```

## Endpoints principales

- `GET /api/health`
- `GET /api/unidades-medicas`
- `GET /api/unidades-medicas?provincia=PICHINCHA&nivel=2&q=hospital`
- `GET /api/unidades-medicas/buscar?q=`
- `GET /api/unidades-medicas/siglas/{siglas}`
- `GET /api/unidades-medicas/{id}`

Swagger UI: `http://localhost:8080/api/swagger-ui/index.html`

## Base de datos

### Oracle DBDVP (perfil `oracle`)

```
jdbc:oracle:thin:@192.168.29.66:1521/DBDVP
usuario: DIRGEN_OWNER
tabla: DIR_UNIDADESMED_TP (101 unidades médicas)
```

### PostgreSQL local (perfil `postgres`)

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

Ver plan de alineación con plantilla: [`Documentacion/PLAN_ALINEACION_PAS-EST-055.md`](../Documentacion/PLAN_ALINEACION_PAS-EST-055.md)

```
Controller → UseCase → RepositoryPort
                           ├── UnidadMedicaOracleRepository (oracle)
                           ├── UnidadMedicaPostgresRepository (postgres)
                           └── UnidadMedicaMockRepository (mock)
```
