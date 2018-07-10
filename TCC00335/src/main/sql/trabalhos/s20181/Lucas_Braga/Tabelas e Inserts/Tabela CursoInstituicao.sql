--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 12:13:27

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
-- TOC entry 206 (class 1259 OID 16510)
-- Name: CursoInstituicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."CursoInstituicao" (
    "idCurso" integer NOT NULL,
    "idInstituicao" integer NOT NULL
);


ALTER TABLE public."CursoInstituicao" OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16506)
-- Name: CursoInstituicao_idCurso_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."CursoInstituicao_idCurso_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."CursoInstituicao_idCurso_seq" OWNER TO postgres;

--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 204
-- Name: CursoInstituicao_idCurso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."CursoInstituicao_idCurso_seq" OWNED BY public."CursoInstituicao"."idCurso";


--
-- TOC entry 205 (class 1259 OID 16508)
-- Name: CursoInstituicao_idInstituicao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."CursoInstituicao_idInstituicao_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."CursoInstituicao_idInstituicao_seq" OWNER TO postgres;

--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 205
-- Name: CursoInstituicao_idInstituicao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."CursoInstituicao_idInstituicao_seq" OWNED BY public."CursoInstituicao"."idInstituicao";


--
-- TOC entry 2042 (class 2604 OID 16513)
-- Name: idCurso; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CursoInstituicao" ALTER COLUMN "idCurso" SET DEFAULT nextval('public."CursoInstituicao_idCurso_seq"'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16514)
-- Name: idInstituicao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CursoInstituicao" ALTER COLUMN "idInstituicao" SET DEFAULT nextval('public."CursoInstituicao_idInstituicao_seq"'::regclass);


--
-- TOC entry 2164 (class 0 OID 16510)
-- Dependencies: 206
-- Data for Name: CursoInstituicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."CursoInstituicao" ("idCurso", "idInstituicao") FROM stdin;
1	1
3	2
2	3
4	4
\.


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 204
-- Name: CursoInstituicao_idCurso_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."CursoInstituicao_idCurso_seq"', 1, false);


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 205
-- Name: CursoInstituicao_idInstituicao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."CursoInstituicao_idInstituicao_seq"', 1, false);


--
-- TOC entry 2045 (class 2606 OID 16516)
-- Name: pk_idCursoInstituicao; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CursoInstituicao"
    ADD CONSTRAINT "pk_idCursoInstituicao" PRIMARY KEY ("idCurso", "idInstituicao");


--
-- TOC entry 2046 (class 2606 OID 16517)
-- Name: fk_idCurso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CursoInstituicao"
    ADD CONSTRAINT "fk_idCurso" FOREIGN KEY ("idCurso") REFERENCES public."Curso"("idCurso");


--
-- TOC entry 2047 (class 2606 OID 16522)
-- Name: fk_idInstituicao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CursoInstituicao"
    ADD CONSTRAINT "fk_idInstituicao" FOREIGN KEY ("idInstituicao") REFERENCES public."Instituicao"("idInstituicao");


-- Completed on 2018-06-29 12:13:28

--
-- PostgreSQL database dump complete
--

