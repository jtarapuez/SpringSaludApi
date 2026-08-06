# SpringSaludApi

API REST Spring Boot para el proyecto de salud IESS: unidades médicas, geolocalización y consultas públicas.

## Stack

- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Oracle DBDVP (`DIR_UNIDADESMED_TP`)
- SpringDoc OpenAPI (Swagger)

## Perfiles

| Perfil | Uso |
|--------|-----|
| `oracle` | **Por defecto.** Conexión a Oracle DBDVP `DIRGEN_OWNER` (`DIR_UNIDADESMED_TP`) |
| `mock` | Datos en memoria desde JSON (desarrollo/tests sin BD) |

## Configuración del entorno (PAS-EST-055)

### Etapa 1 — Variables `.env`

```bash
cp .env.example .env
# Completar DB_ORACLE_PASSWORD
```

| Variable | Descripción |
|----------|-------------|
| `DB_ENGINE` | `oracle` (default) o `mock` |
| `DB_ORACLE_*` | Conexión Oracle DBDVP |

### Etapa 2 — Vault y Docker

| Variable | Descripción |
|----------|-------------|
| `VAULT_ENABLED` | `true` para cargar secretos desde HashiCorp Vault |
| `VAULT_HOST`, `VAULT_PORT`, `VAULT_TOKEN` | Conexión Vault local (`root-token` en dev) |
| `MONGO_ENABLED` | `true` para auditoría Mongo (Etapa 3) |

**Utilitarios locales (Mongo, Vault, MinIO):**

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

## Tests

Unitarios y de controlador (perfil mock):

```bash
mvn test
```

## SonarQube (calidad de código)

Servidor IESS: `http://192.168.111.89:9000`  
Guía: [`docs/SONAR.md`](./docs/SONAR.md)

```bash
mvn clean test
mvn sonar:sonar
```

Requiere profile `sonar` en `~/.m2/settings.xml` (URL + token; el token **no** va en el repo).

## Arquitectura (PAS-EST-055)

Ver plan de alineación con plantilla: [`Documentacion/PLAN_ALINEACION_PAS-EST-055.md`](../Documentacion/PLAN_ALINEACION_PAS-EST-055.md)

```
Controller → UseCase → RepositoryPort
                           ├── UnidadMedicaOracleRepository (oracle)
                           └── UnidadMedicaMockRepository (mock)
```
