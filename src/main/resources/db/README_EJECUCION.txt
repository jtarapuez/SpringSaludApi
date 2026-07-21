Ejecución de scripts PostgreSQL - IESS Salud (local)
====================================================

Entorno detectado:
  - PostgreSQL 16.11 (Homebrew)
  - Host: localhost:5432
  - Usuario admin local: desarrollo
  - Bases existentes: bolsa_empleo, gesempleo_bd_local, postgres

Orden de ejecución en pgAdmin o psql:
--------------------------------------

1) Conectado a base "postgres", ejecutar:
   01_create_database.sql

2) Conectado a base "iess_salud", ejecutar:
   02_schema.sql
   03_seed_provincias.sql

3) Cargar las 101 unidades médicas (pendiente Fase 3):
   - DataLoader Spring Boot desde JSON, o
   - Script generado automáticamente

Conexión Spring Boot (application-postgres.yaml):
  DB_HOST=localhost
  DB_PORT=5432
  DB_NAME=iess_salud
  DB_USER=iess_salud_user
  DB_PASSWORD=iess_salud_dev

Perfil activo:
  spring.profiles.active=mock,postgres
  (o solo postgres cuando JPA esté listo)

Verificación rápida:
  psql -h localhost -d iess_salud -c "\dt salud.*"
  psql -h localhost -d iess_salud -c "SELECT count(*) FROM salud.provincias;"
