# Vault — secretos Oracle `DIRGEN_USR`

Plantillas para **calidad** y **producción**. El path del PLD **no es una carpeta del repo**: es un secreto en el servidor **HashiCorp Vault** (DNTSI).

## Paths (KV v2)

| Uso | Path |
|-----|------|
| Comando CLI (`vault kv put`) | `salud-geolocalizacion-api/database/oracle` |
| Lectura API (Spring Boot) | `salud-geolocalizacion-api/data/database/oracle` |

## Claves obligatorias

| Clave Vault | Variable Spring | Valor fijo / ejemplo |
|-------------|-----------------|----------------------|
| `host` | `DB_ORACLE_HOST` | `<ORACLE_HOST_QA>` o `<ORACLE_HOST_PROD>` |
| `port` | `DB_ORACLE_PORT` | `1521` |
| `service` | `DB_ORACLE_SERVICE` | Definido por DNTSI |
| `username` | `DB_ORACLE_USERNAME` | **`DIRGEN_USR`** |
| `password` | `DB_ORACLE_PASSWORD` | Secreto DNTSI (no en Git) |
| `schema` | `DB_ORACLE_SCHEMA` | **`DIRGEN_USR`** |

## Archivos en este directorio

| Archivo | Quién lo usa |
|---------|--------------|
| `oracle-secret.qa.example.json` | DNTSI — contenido del secreto QA |
| `oracle-secret.prod.example.json` | DNTSI — contenido del secreto PROD |
| `cargar-secreto-oracle.sh.example` | DNTSI — script `vault kv put` |

## Desarrollo local (opcional)

```bash
docker compose -f docker-compose-utilitarios.yml up -d vault
docker compose -f docker-compose-utilitarios.yml up vault-init
```

Usa `vault-init.sh` en la raíz del proyecto (Oracle dev + `DIRGEN_USR`).

En desarrollo normal: `.env` con `VAULT_ENABLED=false` y credenciales en `.env.example`.

## Calidad — orden de trabajo

1. **DNTSI** crea el secreto en Vault QA (JSON o script de este folder).
2. **Operador QA** en el servidor API: `cp .env.qa.example .env` y completa `<VAULT_*>` (sin password Oracle).
3. `docker compose up -d --build` y validar logs + `/api/unidades-medicas` (103 filas).

## Producción

Igual que QA con `.env.prod.example` y `oracle-secret.prod.example.json` en Vault PROD.

Documentación PLD: `Documentacion/calidad/VAULT_Y_ENV_QA_PROD_SALUD-GEO.md`
