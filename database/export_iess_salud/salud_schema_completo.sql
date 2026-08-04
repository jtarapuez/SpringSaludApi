--
-- PostgreSQL database dump
--

\restrict wtgv19IvEox0Su34rltPqjhegcdfafDBS4NuFEF1qDCAAwQtLBKfLtslZzBlHYA

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
-- Data for Name: unidades_medicas; Type: TABLE DATA; Schema: salud; Owner: -
--

COPY salud.unidades_medicas (id_unidad, id_provincia, nom_unidad, siglas, nivel, descripcion, latitud, longitud, telefono, sitio_web, direccion, est_registro, usu_creacion, fec_creacion, ip_equipo, usu_actualizacion, fec_actualizacion, usu_eliminacion, fec_eliminacion) FROM stdin;
83e73ee2-e92a-373e-b235-b6cc1e1b2036	19	HOSPITAL DE ESPECIALIDADES - CARLOS ANDRADE MARÍN	HCAM	2	III NIVEL	-0.2051303	-78.5048297	02-2564939	https://hcam.iess.gob.ec/	18 de Septiembre N19-63 entre Ayacucho y Av. Universitaria	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
3316b90a-22b5-35d0-9de2-06f3bacacd66	19	HOSPITAL GENERAL - SAN FRANCISCO DE QUITO	HGSF	2	II NIVEL	-0.0896561	-78.4767849	02-3952000		Av. Jaime Roldós Aguilera y Juan Ramón Jiménez	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
2b4e210b-7e1a-3e99-b752-ae7926dd6e42	19	HOSPITAL GENERAL - SUR DE QUITO	HGSTO	2	II NIVEL	-0.2574167	-78.5252382	02-398-2700		Moraspungo y Pinllopata	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
fa8b9f7c-6f45-35f7-9b97-276f3f62264c	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa  Sangolquì	HD-SA	2	II NIVEL-3  CCQA-HD	-0.3371986	-78.4408581	02 2080707		García Moreno y Pichincha esquina	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
0fd9ad24-781c-3cab-9b0a-5ee9ae2d1084	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Central Quito	HD-CQ	1	II-3  CCQA-HD	-0.0907445	-78.4756020	02 2653444		Benalcázar N8-12 y  Manabí	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e7bff41c-c729-3af0-a9c1-6bcaf10f6458	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Chimbacalle	HD-CH	1	II-3  CCQA-HD	-0.2446974	-78.5136295	03 2957121		Av. Napo E-164 y Casitagua	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
452bad4a-12c4-3744-a28d-d6cbc0355e11	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Cotocollao	HD-CO	1	II-3  CCQA-HD	-0.1376950	-78.4927812	02 2590080		Av. La Prensa N55-118 y Pulida	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
235dd8e1-8e89-3a13-b6bb-8f10e8230a5d	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa El Batàn	HD-EB	1	II-3  CCQA-HD	-0.1621421	-78.4733919	02 2449369		Av. de las Palmeras y Río Coca	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
67a01a15-14c7-3633-ba68-d7c4280877cb	19	Centro Mèdico Familiar integral  y Especialidades, Diàlisis La Mariscal	CMFIEDM	2	II NIVEL-3  CCQA-HD	-0.2019085	-78.4877323	02 3936100		Av . Colon y Diego de Almagro	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
50fe2c97-6e02-3f99-a42c-d41a0b98ddd9	19	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Eloy Alfaro	HD-EA	2	II NIVEL-3  CCQA-HD	-0.1948085	-78.4941356	02 1675497		Av. Tnte. Hugo Ortiz s/n Ayapamba. Sector  Mercado Mayorista	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e250caa3-2ee9-3d37-b4d5-c4cc9e9a35ca	19	Centro de Especialidades La Ecuatoriana	CE-LE	2	II NIVEL-2    CE	-0.2437636	-78.5319054	02 2696712		Avs. La Ecuatoriana Oe6-133 y  Mariscal Sucre	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e6f64534-2d8f-3d72-97c7-77bb304a6dae	19	Centro de Especialidades Sur Occidental	CE-SO	2	II NIVEL-2    CE	-0.2437636	-78.5319054	02 2613257		Av. Mariscal Sucre 1127 y Hernando Prado- La Magdalena	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
247c6bd0-2ef2-3275-b9c9-eff9aa814d6c	19	Centro de Especialidades Comité del Pueblo	CE-CP	1	II-2    CE	-0.1193301	-78.4775130	02 3968200		Juncal # 100 y Amesaba	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
3f52bbfb-ff60-32df-b616-5d3e725738a4	19	Centro de Salud B Cayambe	CSB-CAY	1	I NIVEL	0.0355728	-78.1500652	022879699-022877182		JUAN MONTALVO 803 Y SUCRE	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
9ad0fd50-55cd-394d-8832-0b258d10814a	19	Centro de Salud B  Tabacundo	CSB-TA	1	I NIVEL	0.0461327	-78.2129584	2366008 2366773		VELASCO IBARRA 206	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
da718db1-9b92-3452-b15b-d645ba6424db	19	Centro de Salud A  Machachi	CSA-MA	1	I NIVEL	-0.5121396	-78.5689163	22314501		11 DE NOVIEMBRE PASAJE JOSE MEJIA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e795876c-43a1-36f2-b808-19e94f10ded7	19	Centro de Salud A  Amaguaña	CSA-AM	1	I NIVEL	-0.3708769	-78.5032701	22877182		Jacinto Jijón y Caamaño s/n y calle Luis Vargas (esquina), Sector Los Cuarteles	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
b97c333c-f92b-3394-917a-cf08a0d63cd8	1	HOSPITAL DE ESPECIALIDADES - JOSÉ CARRASCO ARTEAGA	HEJCA	2	III NIVEL	-2.8987397	-78.9701243	07-2808911		Rayoloma entre Popayán y Pacto Andino	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
ca3a6558-502a-3788-9612-4f73c58e9c21	1	Centro de Especialidades Central Cuenca	CE-CC	2	II NIVEL-2    CE	-2.8991719	-78.9932644	07 2820214		Bolívar 690 y Antonio Borrero	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
675fae5b-865b-39da-80b8-421b6bec1a4f	1	Centro de Salud C Materno Infantil y de Emergencias Cuenca	CSC-MIEC	1	I NIVEL	-2.8991719	-78.9932644	73702270		VIRACOCHABAMBA Y PASEO DE LOS CAÑARIS	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
8fbe4172-659e-3777-8124-dafa62a9fbb1	10	HOSPITAL DE ESPECIALIDADES - TEODORO MALDONADO CARBO	HETMC	2	III NIVEL	-2.2322305	-79.8985307	04-2422808		Av. 25 de Julio y Leonidas Ortega	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
bc0270a0-d1be-3747-ab92-c37b5d913b09	10	HOSPITAL GENERAL - MILAGRO	HGMI	2	II NIVEL	-2.1324465	-79.5790619	04-2970053		Av. Dr. Vicente Asan Ubilla y Manuel Ascazubi	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
09a75f63-37d4-3d39-b3dc-c9e21f634f25	10	HOSPITAL GENERAL - NORTE DE GUAYAQUIL LOS CEIBOS	HGNGC	2	II NIVEL	-2.1758762	-79.9402928	04-3805130		Av. Del bombero y Av. 47A NO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
83ae59b7-ff15-3f75-8aba-feb249a7e57c	10	HOSPITAL BÁSICO - ANCÓN	HBAN	2	II NIVEL	-2.3237759	-80.8560398	04-2906076		Calle 2 Barrio Otavalo	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
fa632404-7280-3897-88a9-923b7b714020	10	HOSPITAL BÁSICO - DURÁN	HBDU	2	II NIVEL	1.2318874	-78.6211230	04-2801023		Gonzalo Aparicio y Guillermo Davis	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
528e99fe-8304-3af3-9977-a98ceaf1eb07	10	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Efren Jurado Lopez	HD-EJL	2	II NIVEL-3  CCQA-HD	-2.2084413	-79.8853207	04 2582421		Chile 2816 y Colombia	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
37815279-91d1-3ac3-9595-f71d63cea628	10	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Norte Tarqui	HD-NT	2	II NIVEL-3  CCQA-HD	-0.2557517	-78.5529021	04 2255188		Av. Juan Tanca Marengo Km. 6,5\r\nFrente Colegio Americano	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
14af0230-917e-3f49-b5d8-3f01a50584a4	10	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Sur Valdivia	HD-SV	1	II-3  CCQA-HD	-2.2457869	-79.8947836	04 2430634		25 de Julio y Calle Napo	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
a884a964-a4cd-32a3-a36c-34a22352435c	10	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Maria Rosa Parra Arreaga	HD-MRPA	2	II NIVEL-3  CCQA-HD	-1.9605642	-79.7266379	en trámite (reciente apertura)		31 de Octubre y Rocafuerte	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
dc40d31d-73e1-3307-98e3-8c2cec55d1af	10	Centro de Especialidades  Balzar	CE-BA	2	II NIVEL-2    CE	-1.3657276	-79.9010633	05 2750305		La Paz S/N y Rómulo Rendón	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
6ca6ec77-ec6f-363e-b49f-42a090dd49af	10	Centro de Especialidades  Daule	CE-DA	2	II NIVEL-2    CE	-1.8701338	-79.9804335	04 2795409		Ciudadela Rosa Mira N.- Solar 1, Mz. 225	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
99928693-819e-39aa-be7e-67b9d89bbbe9	10	Centro de Especialidades Central Guayas	CE-CG	2	II NIVEL-2    CE	-0.9479048	-80.7291010	04 2322177		Alberto Reyna 204 y Villamil, Bahía	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f65343d9-df09-3280-b66a-2556bd0f8bac	10	Centro de Especialidades Letamendi	CE-LE	2	II NIVEL-2    CE	-2.2063659	-79.8889569	04 2403398		Lorenzo de Garaicoa 3029 y Letamendi	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
a93fd76f-c258-35f6-834f-b787d9765d69	10	Centro de Salud B El Empalme	CSB-EE	1	I NIVEL	-1.0519228	-79.6445326	042960212 EXT.102		AV. GUAYAQUIL Y ASCHING	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
906a470f-1ab2-3fb1-86a6-a180284274ad	10	Centro de Salud B  Bucay	CSB-BU	1	I NIVEL	-2.2025135	-79.1388248	0422727092 / 042727441		GARCIA MORENO Y LOJA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
01de4b97-7f1e-3f29-ab98-95fd1f355d70	10	Centro de Salud C Naranjal	CSC-NA	1	I NIVEL	-2.6775875	-79.6161916	42751222		AV. OLMEDO Y CALLEJON IESS A TRES CUADRAS DEL MUNICIPIO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
a17b02e6-a604-3aca-abbd-2e3c7af5289b	23	HOSPITAL GENERAL - AMBATO	HGAM	2	II NIVEL	-1.2316289	-78.6230907	03-2421300		Av. Los Capulíes (Atocha) y Edmundo Martinez	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
a9d83c72-8f0c-36a7-814b-4d466aef3564	23	Centro de Salud A Baños	CCA-BA	1	I NIVEL	-1.4002680	-78.4258418	52685108		AV. ESTUDIANTIL CIUDADELA MUNICIPAL	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
3b0fe27f-b341-3bdb-8a75-1d69e17fed9f	13	HOSPITAL GENERAL - BABAHOYO	HGBA	2	II NIVEL	-1.8051476	-79.5216097	05-2735162		27 de Mayo y general Barona	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
b3142282-3a39-32d0-82c5-818d4d2fa18a	13	HOSPITAL GENERAL - QUEVEDO	HGQUE	2	II NIVEL	-1.0279389	-79.4677979	05-3702390		Av. San Rafael y calle 46	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
2d5bd020-b399-32ec-b8cd-cb71ed7151e3	11	HOSPITAL GENERAL - IBARRA	HGIB	2	II NIVEL	0.3596543	-78.1282157	06-2958193		Av. Víctor Manuel Guzmán entre Bolivia y Uruguay	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
5510f1f0-735c-3c44-abb5-a83a73e31906	11	Centro de Especialidades Otavalo	CE-OT	2	II NIVEL-2    CE	0.2265868	-78.2625839	06 2920428		Roca #81 y Abdón Calderón	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f1c9e0ed-ba21-33cd-8165-b3adf0d7c796	11	Centro de Salud B  San Gabriel	CSB-SG	1	I NIVEL	0.5915572	-77.8318101	62292037 EXT. 1		AV. ATAHUALPA Y RUMICHACA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
ceda02a5-6de7-3f9a-ac7d-108d3e3df2fd	11	Centro de Salud B Atuntaqui	CSB-AT	1	I NIVEL	0.3307994	-78.2148248	62906125		AV. GENERAL ENRÍQUEZ 12-43 Y ATAHUALPA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
b285824d-47f6-3277-8d77-c0404ac50e3a	11	Centro de Salud A  Cotacachi	CSA-CO	1	I NIVEL	0.2296592	-78.6308887	62916027		PEDRO MONCAYO 15-29 Y M. PENAHERRERA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
6db7b436-8715-3e6f-8c63-0c4878185824	6	HOSPITAL GENERAL - LATACUNGA	HGLA	2	II NIVEL	-0.9368621	-78.6169158	03-2997503		Calle Quito S/N Y Leopoldo Pino	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
ce9c2210-8fe9-3572-ac3e-bbf8a2dee899	7	HOSPITAL GENERAL - MACHALA	HGMACH	2	II NIVEL	-3.2813507	-79.9454625	07-2962330		Av. Alejandro Castro Benitez y Vía Pajonal	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f4cd057d-363f-31a3-a04f-caad89098a7e	14	HOSPITAL GENERAL - MANTA	HGMAN	2	II NIVEL	-0.9549726	-80.7416069	05-3702910		Vía Manta - Montecristi  y  Av. Interbarrial	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
27b6089a-845c-3a5c-b05e-dc3c80fecc57	14	HOSPITAL GENERAL - PORTOVIEJO	HGPO	2	II NIVEL	-1.0441933	-80.4719243	05-2635313		Av. Manabí y Manuel Palomeque	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
beeb3cc1-3d28-357d-94d0-62829a75b145	14	HOSPITAL BÁSICO - CHONE	HBCH	2	II NIVEL	-0.6900671	-80.0964457	05-2696411		Junín y Boyacá	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f059fdb3-3e41-335a-9ef4-33938acd11b3	14	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Jipijapa	HD-JI	2	II NIVEL-3  CCQA-HD	-1.3526039	-80.5847330	05 2600337		Km.1 Vía Puerto Cayo	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
aa3b3564-f9aa-32e0-ad04-0dcc135354ac	14	Centro de Salud B  Portoviejo	CSB-POJ	1	I NIVEL	-1.0705915	-80.4500839	52632113		COLON Y OLMEDO ESQ.	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
5bc8e0ab-e3e0-3daf-85b6-0623ed64d1cc	14	Centro de Salud C Materno Infantl y de Emergencias Bahía de Caráquez	CSC-BC	1	I NIVEL	-0.6261043	-80.4275373	52690479 ext 21		EFRAIN CENTENO MEDRANA Y JUAN CHÁVEZ MEZA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
49c79cb5-0dd2-36bf-980a-548521d8dbf6	14	Centro de Salud A Paján	CSA-PA	1	I NIVEL	-1.5617641	-80.4431372	52649501-052649255		ZAPOTAL S/N ENTRADA SAN MIGUEL	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
30262029-bf2f-3c6e-85e6-913c342d275d	14	Centro de Salud A Los Esteros	CSA-LE	1	I NIVEL	-0.9511748	-80.7056220	52390018		AV. 103 CALLE 115 Y 116	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
bbeaa0e7-0896-3c70-87ef-ba443322edde	14	Centro de Salud A El Carmen	CSA-EC	1	I NIVEL	-3.1481600	-79.2520451	52660014		URB. CARMEN, AV. 3 DE JULIO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e4781886-4bae-3f98-b7d6-8189829d6798	14	Centro de Salud A Calceta	CSA-CAL	1	I NIVEL	-0.8456098	-80.1766810	52685108		AV. ESTUDIANTIL CIUDADELA MUNICIPAL	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
314fd224-68c1-323b-89e8-203028549920	12	HOSPITAL GENERAL - MANUEL YGNACIO MONTEROS	HGMYM	2	II NIVEL	-3.9846174	-79.2028051	07-2563279		Av. Nueva Loja y Calle Ibarra	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
0416de5e-50b9-38cb-bff7-d4cfe0fcf901	12	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Central Loja	HD-CL	2	II NIVEL-3  CCQA-HD	-3.9953717	-79.2017615	07 2584851		10 de Agosto y Manuel Agustín Aguirre	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
afde1604-7224-3abb-8373-6f1d32b3e713	12	Centro de Salud B Paltas	CSB-PAL	1	I NIVEL	-4.0553576	-79.6454448	72683165		LAURO GUERRERO Y MERCADILLO ESQUINA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
86e2707a-427f-3502-87e8-6ed9808e7a7b	12	Centro de Salud B Catamayo	CSB-CAT	1	I NIVEL	-3.9865172	-79.3593586	72676883		18 DE NOVIEMBRE Y  BOLÍVAR	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
23a99ccb-cb50-38a6-8614-c0baddd7fda9	12	Centro de Salud B  Macará	CSB-MA	1	I NIVEL	-4.3777150	-79.9434804	072694063 /072269490		JUVENAL JARRAMILLO Y GONZANAMA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
66eb7622-b741-3c79-8b1d-9186d26639a3	12	Centro de Salud B  Celica	CSB-CE	1	I NIVEL	-4.0996518	-79.9568688	72657742		BARRIO SAN VICENTE, AMAZONAS Y RAFAEL GRANDA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f268c3aa-258c-30e6-836c-f7a4b43de263	12	Centro de Salud B  Cariamanga	CSB-CAR	1	I NIVEL	-4.3288725	-79.5573467	72688185		CHILE ENTRE ROCAFUERTE Y CUARTO CENTENARIO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
8dbf7310-6520-34ab-93e7-0aa2f8d0e526	5	HOSPITAL GENERAL - RIOBAMBA	HGRI	2	II NIVEL	-1.6823063	-78.6439163	03-2968074		Chile 3929 y Av. Unidad Nacional	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
335248ee-5960-3c81-b911-ef9f97ebd6fe	21	HOSPITAL GENERAL - SANTO DOMINGO	HGI	2	II NIVEL	-0.2334802	-79.1724493	08-3940800		Av. Río Lelia y calle Tumbecinos	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
182a256b-213c-3020-99df-24428671303f	21	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Santo Domingo	HD-SD	2	II NIVEL-3  CCQA-HD	-0.2057879	-78.5130892	02 2750430		Av. Quito Km 1 s/n y Los Naranjos	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
5e443134-d278-3566-97ea-9ab82365018d	18	HOSPITAL BÁSICO - EL PUYO	HBEP	2	II NIVEL	-1.4870108	-78.0103272	03-2885761		Av. Celao Marín 1021 y Curaray	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
a165a0b8-14d1-3c62-90e6-815eed97e148	8	HOSPITAL BÁSICO - ESMERALDAS	HBE	2	II NIVEL	1.0880215	-78.9899709	06-2712465		Avenidas Colón y Guayas	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e5e91adb-2538-3d60-a708-4f5d03f6ac68	8	Centro de Salud B Quinindé	CSB-QUI	1	I NIVEL	0.3303361	-79.4607695	062736692 ext. 105		BARRIO NUEVO QUININDE, SECTOR NUEVOS HORIZONTES ALTO, TRAS ANTIGUO COL 3 DE JULIO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
6f4ffad8-be3e-3e70-95af-061052b235d4	8	Centro de Salud A San Lorenzo	CSA-SL	1	I NIVEL	-2.1767392	-79.8796067	62781866		ROBERTO LUIS CERVANTES Y RAMÓN CHIRIBOGA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
9cabf87b-b724-3685-88f3-30d8c49b7707	2	HOSPITAL BÁSICO - GUARANDA	HBGU	2	II NIVEL	-1.5816040	-78.9995653	03-2982019		Augusto Chávez S/N  vía a Ambato	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
51efa529-0f56-3144-92fa-f104304420ce	16	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa  El Tena	HD-ET	2	II NIVEL-3  CCQA-HD	-1.0114606	-77.8097684	03 2873668		Calle 9 de Octubre S/N y Tarqui	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
5897da9f-64b9-3d22-ac45-3806f3fe111e	4	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Azogues	HD-AZ	2	II NIVEL-3  CCQA-HD	-2.7368824	-78.8407016	07 2240256		Gral. Veintimilla 584 entre Ayacucho y VI	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
bcd807ab-a8c1-3dea-80e3-1552cf0a5f3d	4	Centro Clínico Quirúrgico Ambulatorio Hospital del Día La Troncal	HD-LT	2	II NIVEL-3  CCQA-HD	-2.4219249	-79.3331945	07 2420178		4 de Noviembre y Napo	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
3382570a-3b50-3a99-8dfb-1d4645ad163a	4	CRIE-Centro de Rehabilitación Integral Especializado Azogues	CRIE	1	CRIE-A	-2.7384273	-78.8525147	07 3702240		Av 16 de Abril	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
c5299505-7533-345b-ad47-f2d7eb0f0156	4	Centro de Salud B  Cañar	CSB-CA	1	I NIVEL	-2.7701595	-78.9189059	72235058		AV. PASEO DE LOS CAÑARIS, FRENTE AL ESTADIO 26 DE ENERO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
6daf2324-62c2-3e93-951b-16b2caf97275	15	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Macas	HD-MA	2	II NIVEL-3  CCQA-HD	-2.2827155	-78.1247473	07 2702558		Av. de la Ciudad y Barranca	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
04711ad8-bad3-350d-bfd0-634e9c5059d4	15	Centro de Salud B Sucúa	CSB-SU	1	I NIVEL	-2.4650926	-78.1718867	72740889		AV. JULIO AROSEMENA MONROY Y CARLOS PALACIOS	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
52fdd546-17fb-3508-af8e-1dce0cd405eb	15	Centro de Salud A Gualaquiza	CSA-GU	1	I NIVEL	-3.4073152	-78.4853220	72781468		CALLE RIO SANTIAGO, ENTRE VENANCIO CALLE Y TIWINTZA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
7c93a677-608a-3e3e-b469-6c3b7b4fad39	24	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Zamora	HD-ZA	2	II NIVEL-3  CCQA-HD	-4.0657634	-78.9526582	07 2605117		Av. Alonso de Mercadillo y Paquisha	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
30508c74-019f-30fe-b106-710bea0f7ce7	22	Centro Clinico Quirurgico Ambulatorio Hospital del Día  Nueva Loja	HD-TU	1	II-3  CCQA-HD	-3.9953717	-79.2017615	06 2830615		Jorge Añazco Nro. 140 y Pasaje Gonzanamá	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
86776723-53bf-3a48-be7c-56d3ba48413b	3	Centro Clìnico Quirùrgico Ambulatorio Hospital del Dìa Tulcán	HD-NL	1	II-3  CCQA-HD	0.8168151	-77.7210199	06 2981238		Rafael Arellano entre Boyaca Y García Moreno S/N	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
74486c6a-9374-3a76-9ab5-a35b364981ce	20	Centro de Especialidades La Libertad	CE-LL	2	II NIVEL-2    CE	-0.1850068	-78.4942732	04 2785226		Calle 10 y Av. 1314\r\nBarrio San Francisco	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
0e321d87-bf9d-3a2f-b91a-1ee75f434e34	5	Puesto de Salud  Chunchi	PSCH	1	I NIVEL	-2.2886946	-78.9195378	32937194		GENERAL MORALES S/N Y GENERAL CÓRDOVA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f5a30778-1f5c-3279-82bc-be41f9cbed14	5	Centro de Salud B Alausí	CSB-AL	1	I NIVEL	-2.2056751	-78.8486595	32930210		ESTEBAN OROZCO Y ANTTON MORA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
2fc86360-a987-30ad-bf67-b1e472a5e879	5	Centro de Salud A  Píllaro	CSA-PI	1	I NIVEL	-1.1764062	-78.5521460	32873668 EXT.16		CALLE URBINA Y ATIPILLAHUAZO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
c885434c-f67d-3ca4-a70c-fb11f715bd4e	7	Centro de Salud B Santa Rosa	CSB-SR	1	I NIVEL	-2.1995346	-79.1142197	72943210		LEONY CASTELLY Y JOSE M OLLAGUE	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
91c12a11-db48-312e-a7af-91d2ae659d21	7	Centro de Salud B Portovelo	CSB-POL	1	I NIVEL	-3.7156668	-79.6201133	072949184 /072949690		TOMÁS CARRIÓN  YWELMER QUEZADA NEIRA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
db1d94aa-8ad9-33db-80f6-bdf3c21a2512	7	Centro de Salud B Piñas	CSB-PI	1	I NIVEL	-2.0786829	-79.5855665	72976159		CDLA. ORQUÍDEA SUR - AVDA. FRANCISCO CARRIÓN	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
21b5c7e2-f734-3559-a421-c49d2aba3e20	7	Centro de Salud B Pasaje	CSB-PAS	1	I NIVEL	-0.2159932	-78.4086260	72915173		ELOY ALFARO Y TARQUI	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
ae67b42c-21b7-33a4-a8cb-13c6ecdd1cec	7	Centro de Salud B Huaquillas	CSB-HU	1	I NIVEL	-3.4818942	-80.2289829	72510392		AV. DE LA REPUBLICA Y MANABI	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
f9d13ccc-6c2a-31fb-9d91-f8ad1bb113e3	7	Centro de Salud C Materno Infantil y de Emergencias  Zaruma	CSC-MIEZ	1	I NIVEL	-3.6969993	-79.6118303	72972130		AV. ISIDRO AYORA Y LOS POMARROSOS BARRIO 16 DE JULIO	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
50b9c288-1c07-3e78-ae2a-d9ef71c714a6	2	Centro de Salud B San Miguel de Bolívar	CSB-SMB	1	I NIVEL	-1.9457986	-79.0633237	32650554- 32989448		BATALLA CAMINO REAL S/N Y ELOY VILLAGOMEZ	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
b93a24c6-0c74-330a-978e-757757296727	13	Centro de Salud A Vinces	CSA-VI	1	I NIVEL	-1.5557497	-79.7529923	52790170		BOLIVAR Y SUCRE	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
189c391f-11fe-3814-8229-f143cf921cae	13	Centro de Salud A Ventanas	CSA-VE	1	I NIVEL	-1.4441640	-79.4604807	052790368 / 052972991		9 DE OCTUBRE 302 Y VELASCO IBARRA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
2c8193e0-7bf9-3f12-bddc-822a4afa68f6	9	Centro de Salud A San Cristóbal	CSA-SC	1	I NIVEL	-0.2917327	-78.5312910	52520306		NARCISO OLAYA Y JUAN JOSÉ FLORES	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
98305810-3915-38fb-8b42-f892a13b7cc6	9	Centro de Salud A  Santa Cruz	CSA-SCZ	1	I NIVEL	-1.9457986	-79.0633237	52526310		CALLE DELFIN Y DUNCAN S/N	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
e70521ce-32c8-3222-874b-af68e09d8d1b	25	Centro de Salud A  Zumba	CSA-ZU	1	I NIVEL	-4.8629461	-79.1350644	72308433		12 DE FEBRERO Y ORELLANA	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
57883fda-5ec8-3145-9a8c-98f6cadee9a0	17	Centro de Salud A  El Coca	CSA-EC	1	I NIVEL	-0.4669999	-76.9912280	06 2880452		CAMILO DE TORRANO Y MODESTO VALLE	A	system	2026-06-18 16:58:53.003866	\N	\N	\N	\N	\N
10b79173-be82-3eeb-a8db-b168235a5647	3	Centro de Salud B  El Ángel	CSB-EA	1	I NIVEL	0.6226247	-77.9442591	\N		ABDON CALDERON Y RIO FRIO	A	system	2026-06-18 16:58:53.003866	\N	prueba-dev	2026-07-22 16:48:16.99394	\N	\N
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

\unrestrict wtgv19IvEox0Su34rltPqjhegcdfafDBS4NuFEF1qDCAAwQtLBKfLtslZzBlHYA

