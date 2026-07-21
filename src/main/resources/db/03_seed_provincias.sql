-- =============================================================================
-- Script 03: Catálogo de provincias (25 provincias normalizadas del JSON)
-- Ejecutar conectado a "iess_salud" DESPUÉS de 02_schema.sql
-- =============================================================================

INSERT INTO salud.provincias (nom_provincia) VALUES
    ('AZUAY'),
    ('BOLÍVAR'),
    ('CARCHI'),
    ('CAÑAR'),
    ('CHIMBORAZO'),
    ('COTOPAXI'),
    ('EL ORO'),
    ('ESMERALDAS'),
    ('GALÁPAGOS'),
    ('GUAYAS'),
    ('IMBABURA'),
    ('LOJA'),
    ('LOS RÍOS'),
    ('MANABÍ'),
    ('MORONA SANTIAGO'),
    ('NAPO'),
    ('ORELLANA'),
    ('PASTAZA'),
    ('PICHINCHA'),
    ('SANTA ELENA'),
    ('SANTO DOMINGO'),
    ('SUCUMBÍOS'),
    ('TUNGURAHUA'),
    ('ZAMORA'),
    ('ZAMORA CHINCHIPE')
ON CONFLICT (nom_provincia) DO NOTHING;
