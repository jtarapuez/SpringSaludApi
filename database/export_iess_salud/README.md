# Export PostgreSQL — iess_salud

Fecha: 2026-07-30  
Origen: `localhost:5432` / BD `iess_salud` / schema `salud`

## Contenido exportado

| Tabla | Registros |
|-------|-----------|
| `salud.provincias` | 25 |
| `salud.unidades_medicas` | 101 |

## Archivos

| Archivo | Descripción |
|---------|-------------|
| `iess_salud_completo_YYYYMMDD_HHMMSS.sql` | Dump completo (schema + datos) en SQL |
| `iess_salud_completo_YYYYMMDD_HHMMSS.dump` | Dump completo en formato custom (`pg_restore`) |
| `iess_salud_schema_only.sql` | Solo estructura (sin datos) |
| `iess_salud_data_only.sql` | Solo datos (sin CREATE) |
| `salud_schema_completo.sql` | Solo schema `salud` (estructura + datos) |
| `01_provincias.sql` | Tabla `salud.provincias` |
| `02_unidades_medicas.sql` | Tabla `salud.unidades_medicas` |

## Restaurar (referencia)

```bash
# Crear BD vacía (si no existe)
createdb -h localhost iess_salud

# Restaurar desde SQL
psql -h localhost -d iess_salud -f iess_salud_completo_YYYYMMDD_HHMMSS.sql

# O desde dump custom
pg_restore -h localhost -d iess_salud --no-owner --no-acl iess_salud_completo_YYYYMMDD_HHMMSS.dump
```

## Nota

Esta carpeta es un **respaldo/export**. Los scripts de creación originales del proyecto siguen en:

`src/main/resources/db/`
