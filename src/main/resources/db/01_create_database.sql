-- =============================================================================
-- IESS Salud - PaginaSalud / BaseSpringApi
-- Script 01: Crear base de datos y usuario (ejecutar conectado a "postgres")
-- Usuario local detectado: desarrollo | PostgreSQL 16 | puerto 5432
-- =============================================================================

-- Crear base de datos dedicada (patrón: bolsa_empleo, gesempleo_bd_local)
CREATE DATABASE iess_salud
    WITH OWNER = desarrollo
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Usuario de aplicación (patrón: bolsa_empleo_user)
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'iess_salud_user') THEN
        CREATE ROLE iess_salud_user WITH LOGIN PASSWORD 'iess_salud_dev';
    END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE iess_salud TO iess_salud_user;
