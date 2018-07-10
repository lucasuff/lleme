--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 11:51:15

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
-- TOC entry 186 (class 1259 OID 16411)
-- Name: Curso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Curso" (
    "idCurso" integer NOT NULL,
    "nomeCurso" character varying(80) NOT NULL
);


ALTER TABLE public."Curso" OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16409)
-- Name: Curso_idCurso_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Curso_idCurso_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Curso_idCurso_seq" OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 185
-- Name: Curso_idCurso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Curso_idCurso_seq" OWNED BY public."Curso"."idCurso";


--
-- TOC entry 2042 (class 2604 OID 16414)
-- Name: idCurso; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Curso" ALTER COLUMN "idCurso" SET DEFAULT nextval('public."Curso_idCurso_seq"'::regclass);


--
-- TOC entry 2160 (class 0 OID 16411)
-- Dependencies: 186
-- Data for Name: Curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Curso" ("idCurso", "nomeCurso") FROM stdin;
1	Sistemas de Informação
2	Ciência da Computação
3	Direito
4	Biotecnologia
\.


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 185
-- Name: Curso_idCurso_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Curso_idCurso_seq"', 1, false);


--
-- TOC entry 2044 (class 2606 OID 16416)
-- Name: pk_idCurso; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Curso"
    ADD CONSTRAINT "pk_idCurso" PRIMARY KEY ("idCurso");


-- Completed on 2018-06-29 11:51:16

--
-- PostgreSQL database dump complete
--

