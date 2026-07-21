-- =============================================================================
-- Script 02: Esquema y tablas (ejecutar conectado a "iess_salud")
-- =============================================================================

CREATE SCHEMA IF NOT EXISTS salud AUTHORIZATION desarrollo;
GRANT USAGE ON SCHEMA salud TO iess_salud_user;
GRANT CREATE ON SCHEMA salud TO iess_salud_user;
ALTER ROLE iess_salud_user SET search_path TO salud, public;

-- Catálogo de provincias
CREATE TABLE IF NOT EXISTS salud.provincias (
    id_provincia      SERIAL PRIMARY KEY,
    nom_provincia     VARCHAR(100) NOT NULL,
    cod_provincia     VARCHAR(10),
    est_registro      CHAR(1) NOT NULL DEFAULT 'A'
                        CHECK (est_registro IN ('A', 'I', 'E')),
    usu_creacion      VARCHAR(50) NOT NULL DEFAULT 'system',
    fec_creacion      TIMESTAMP NOT NULL DEFAULT NOW(),
    ip_equipo         VARCHAR(45),
    usu_actualizacion VARCHAR(50),
    fec_actualizacion TIMESTAMP,
    CONSTRAINT uk_provincias_nom UNIQUE (nom_provincia)
);

-- Unidades médicas
CREATE TABLE IF NOT EXISTS salud.unidades_medicas (
    id_unidad         UUID PRIMARY KEY,
    id_provincia      INTEGER NOT NULL REFERENCES salud.provincias(id_provincia),
    nom_unidad        VARCHAR(500) NOT NULL,
    siglas            VARCHAR(20) NOT NULL,
    nivel             SMALLINT NOT NULL CHECK (nivel BETWEEN 1 AND 3),
    descripcion       VARCHAR(200),
    latitud           NUMERIC(10, 7) NOT NULL,
    longitud          NUMERIC(10, 7) NOT NULL,
    telefono          VARCHAR(50),
    sitio_web         VARCHAR(500),
    direccion         VARCHAR(500),
    est_registro      CHAR(1) NOT NULL DEFAULT 'A'
                        CHECK (est_registro IN ('A', 'I', 'E')),
    usu_creacion      VARCHAR(50) NOT NULL DEFAULT 'system',
    fec_creacion      TIMESTAMP NOT NULL DEFAULT NOW(),
    ip_equipo         VARCHAR(45),
    usu_actualizacion VARCHAR(50),
    fec_actualizacion TIMESTAMP,
    usu_eliminacion   VARCHAR(50),
    fec_eliminacion   TIMESTAMP,
    -- Siglas pueden repetirse en distinta provincia (CE-LE, CSA-EC)
    CONSTRAINT uk_unidades_siglas_provincia UNIQUE (siglas, id_provincia)
);

CREATE INDEX IF NOT EXISTS idx_unidades_provincia ON salud.unidades_medicas(id_provincia);
CREATE INDEX IF NOT EXISTS idx_unidades_nivel ON salud.unidades_medicas(nivel);
CREATE INDEX IF NOT EXISTS idx_unidades_estado ON salud.unidades_medicas(est_registro);
CREATE INDEX IF NOT EXISTS idx_unidades_siglas ON salud.unidades_medicas(siglas);
CREATE INDEX IF NOT EXISTS idx_unidades_geo ON salud.unidades_medicas(latitud, longitud);

-- Permisos para el usuario de aplicación
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA salud TO iess_salud_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA salud TO iess_salud_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA salud
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO iess_salud_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA salud
    GRANT USAGE, SELECT ON SEQUENCES TO iess_salud_user;

COMMENT ON TABLE salud.provincias IS 'Catálogo de provincias de Ecuador';
COMMENT ON TABLE salud.unidades_medicas IS 'Unidades médicas IESS para mapa de geolocalización';
