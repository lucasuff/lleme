--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 12:01:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16475)
-- Name: Tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Tipo" (
    "idTipo" integer NOT NULL,
    descricao character varying(20) NOT NULL
);


ALTER TABLE public."Tipo" OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16473)
-- Name: Tipo_idTipo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Tipo_idTipo_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Tipo_idTipo_seq" OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 198
-- Name: Tipo_idTipo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Tipo_idTipo_seq" OWNED BY public."Tipo"."idTipo";


--
-- TOC entry 2042 (class 2604 OID 16478)
-- Name: idTipo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tipo" ALTER COLUMN "idTipo" SET DEFAULT nextval('public."Tipo_idTipo_seq"'::regclass);


--
-- TOC entry 2160 (class 0 OID 16475)
-- Dependencies: 199
-- Data for Name: Tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Tipo" ("idTipo", descricao) FROM stdin;
1	Particular
2	Pública
\.


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 198
-- Name: Tipo_idTipo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Tipo_idTipo_seq"', 1, false);


--
-- TOC entry 2044 (class 2606 OID 16480)
-- Name: pk_idTipo; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tipo"
    ADD CONSTRAINT "pk_idTipo" PRIMARY KEY ("idTipo");


-- Completed on 2018-06-29 12:01:26

--
-- PostgreSQL database dump complete
--

