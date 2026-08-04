--
-- PostgreSQL database dump
--

\restrict tp2clEJuIcM7qZNoOXEVAKUMNwAlYujadB3Z9WxcLUPUfjUfIOAiZHyUiqhzFrE

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
-- Name: provincias id_provincia; Type: DEFAULT; Schema: salud; Owner: -
--

ALTER TABLE ONLY salud.provincias ALTER COLUMN id_provincia SET DEFAULT nextval('salud.provincias_id_provincia_seq'::regclass);


--
-- Data for Name: provincias; Type: TABLE DATA; Schema: salud; Owner: -
--

COPY salud.provincias (id_provincia, nom_provincia, cod_provincia, est_registro, usu_creacion, fec_creacion, ip_equipo, usu_actualizacion, fec_actualizacion) FROM stdin;
1	AZUAY	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
2	BOLÍVAR	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
3	CARCHI	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
4	CAÑAR	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
5	CHIMBORAZO	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
6	COTOPAXI	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
7	EL ORO	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
8	ESMERALDAS	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
9	GALÁPAGOS	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
10	GUAYAS	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
11	IMBABURA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
12	LOJA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
13	LOS RÍOS	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
14	MANABÍ	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
15	MORONA SANTIAGO	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
16	NAPO	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
17	ORELLANA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
18	PASTAZA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
19	PICHINCHA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
20	SANTA ELENA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
21	SANTO DOMINGO	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
22	SUCUMBÍOS	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
23	TUNGURAHUA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
24	ZAMORA	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
25	ZAMORA CHINCHIPE	\N	A	system	2026-06-18 16:48:36.003441	\N	\N	\N
\.


--
-- Name: provincias_id_provincia_seq; Type: SEQUENCE SET; Schema: salud; Owner: -
--

SELECT pg_catalog.setval('salud.provincias_id_provincia_seq', 25, true);


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
-- PostgreSQL database dump complete
--

\unrestrict tp2clEJuIcM7qZNoOXEVAKUMNwAlYujadB3Z9WxcLUPUfjUfIOAiZHyUiqhzFrE

