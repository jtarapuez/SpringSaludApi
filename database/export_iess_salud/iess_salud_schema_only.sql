--
-- PostgreSQL database dump
--

\restrict tfCKOqkM5DIQuWdN9d56XkHhNflKlk8dIPCSf3felpOhg0iIvTb2jjiZNuIm7uV

-- Dumped from database version 16.11 (Homebrew)
-- Dumped by pg_dump version 16.11 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: salud; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA salud;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: provincias; Type: TABLE; Schema: salud; Owner: -
--

CREATE TABLE salud.provincias (
    id_provincia integer NOT NULL,
    nom_provincia character varying(100) NOT NULL,
    cod_provincia character varying(10),
    est_registro character(1) DEFAULT 'A'::bpchar NOT NULL,
    usu_creacion character varying(50) DEFAULT 'system'::character varying NOT NULL,
    fec_creacion timestamp without time zone DEFAULT now() NOT NULL,
    ip_equipo character varying(45),
    usu_actualizacion character varying(50),
    fec_actualizacion timestamp without time zone,
    CONSTRAINT provincias_est_registro_check CHECK ((est_registro = ANY (ARRAY['A'::bpchar, 'I'::bpchar, 'E'::bpchar])))
);


--
-- Name: TABLE provincias; Type: COMMENT; Schema: salud; Owner: -
--

COMMENT ON TABLE salud.provincias IS 'Catálogo de provincias de Ecuador';


--
-- Name: provincias_id_provincia_seq; Type: SEQUENCE; Schema: salud; Owner: -
--

CREATE SEQUENCE salud.provincias_id_provincia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: provincias_id_provincia_seq; Type: SEQUENCE OWNED BY; Schema: salud; Owner: -
--

ALTER SEQUENCE salud.provincias_id_provincia_seq OWNED BY salud.provincias.id_provincia;


--
-- Name: unidades_medicas; Type: TABLE; Schema: salud; Owner: -
--

CREATE TABLE salud.unidades_medicas (
    id_unidad uuid NOT NULL,
    id_provincia integer NOT NULL,
    nom_unidad character varying(500) NOT NULL,
    siglas character varying(20) NOT NULL,
    nivel smallint NOT NULL,
    descripcion character varying(200),
    latitud numeric(10,7) NOT NULL,
    longitud numeric(10,7) NOT NULL,
    telefono character varying(50),
    sitio_web character varying(500),
    direccion character varying(500),
    est_registro character(1) DEFAULT 'A'::bpchar NOT NULL,
    usu_creacion character varying(50) DEFAULT 'system'::character varying NOT NULL,
    fec_creacion timestamp without time zone DEFAULT now() NOT NULL,
    ip_equipo character varying(45),
    usu_actualizacion character varying(50),
    fec_actualizacion timestamp without time zone,
    usu_eliminacion character varying(50),
    fec_eliminacion timestamp without time zone,
    CONSTRAINT unidades_medicas_est_registro_check CHECK ((est_registro = ANY (ARRAY['A'::bpchar, 'I'::bpchar, 'E'::bpchar]))),
    CONSTRAINT unidades_medicas_nivel_check CHECK (((nivel >= 1) AND (nivel <= 3)))
);


--
-- Name: TABLE unidades_medicas; Type: COMMENT; Schema: salud; Owner: -
--

COMMENT ON TABLE salud.unidades_medicas IS 'Unidades médicas IESS para mapa de geolocalización';


--
-- Name: provincias id_provincia; Type: DEFAULT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.provincias ALTER COLUMN id_provincia SET DEFAULT nextval('salud.provincias_id_provincia_seq'::regclass);


--
-- Name: provincias provincias_pkey; Type: CONSTRAINT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.provincias
    ADD CONSTRAINT provincias_pkey PRIMARY KEY (id_provincia);


--
-- Name: provincias uk_provincias_nom; Type: CONSTRAINT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.provincias
    ADD CONSTRAINT uk_provincias_nom UNIQUE (nom_provincia);


--
-- Name: unidades_medicas uk_unidades_siglas_provincia; Type: CONSTRAINT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.unidades_medicas
    ADD CONSTRAINT uk_unidades_siglas_provincia UNIQUE (siglas, id_provincia);


--
-- Name: unidades_medicas unidades_medicas_pkey; Type: CONSTRAINT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.unidades_medicas
    ADD CONSTRAINT unidades_medicas_pkey PRIMARY KEY (id_unidad);


--
-- Name: idx_unidades_estado; Type: INDEX; Schema: salud; Owner: -
--

CREATE INDEX idx_unidades_estado ON salud.unidades_medicas USING btree (est_registro);


--
-- Name: idx_unidades_geo; Type: INDEX; Schema: salud; Owner: -
--

CREATE INDEX idx_unidades_geo ON salud.unidades_medicas USING btree (latitud, longitud);


--
-- Name: idx_unidades_nivel; Type: INDEX; Schema: salud; Owner: -
--

CREATE INDEX idx_unidades_nivel ON salud.unidades_medicas USING btree (nivel);


--
-- Name: idx_unidades_provincia; Type: INDEX; Schema: salud; Owner: -
--

CREATE INDEX idx_unidades_provincia ON salud.unidades_medicas USING btree (id_provincia);


--
-- Name: idx_unidades_siglas; Type: INDEX; Schema: salud; Owner: -
--

CREATE INDEX idx_unidades_siglas ON salud.unidades_medicas USING btree (siglas);


--
-- Name: unidades_medicas unidades_medicas_id_provincia_fkey; Type: FK CONSTRAINT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.unidades_medicas
    ADD CONSTRAINT unidades_medicas_id_provincia_fkey FOREIGN KEY (id_provincia) REFERENCES salud.provincias(id_provincia);


--
-- PostgreSQL database dump complete
--

\unrestrict tfCKOqkM5DIQuWdN9d56XkHhNflKlk8dIPCSf3felpOhg0iIvTb2jjiZNuIm7uV

