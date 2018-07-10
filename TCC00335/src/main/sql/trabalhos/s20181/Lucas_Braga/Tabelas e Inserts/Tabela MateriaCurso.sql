--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 12:14:44

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
-- TOC entry 197 (class 1259 OID 16456)
-- Name: MateriaCurso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."MateriaCurso" (
    "idMateria" integer NOT NULL,
    "idCurso" integer NOT NULL
);


ALTER TABLE public."MateriaCurso" OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16454)
-- Name: MateriaCurso_idCurso_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."MateriaCurso_idCurso_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MateriaCurso_idCurso_seq" OWNER TO postgres;

--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 196
-- Name: MateriaCurso_idCurso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."MateriaCurso_idCurso_seq" OWNED BY public."MateriaCurso"."idCurso";


--
-- TOC entry 195 (class 1259 OID 16452)
-- Name: MateriaCurso_idMateria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."MateriaCurso_idMateria_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MateriaCurso_idMateria_seq" OWNER TO postgres;

--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 195
-- Name: MateriaCurso_idMateria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."MateriaCurso_idMateria_seq" OWNED BY public."MateriaCurso"."idMateria";


--
-- TOC entry 2042 (class 2604 OID 16459)
-- Name: idMateria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MateriaCurso" ALTER COLUMN "idMateria" SET DEFAULT nextval('public."MateriaCurso_idMateria_seq"'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16460)
-- Name: idCurso; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MateriaCurso" ALTER COLUMN "idCurso" SET DEFAULT nextval('public."MateriaCurso_idCurso_seq"'::regclass);


--
-- TOC entry 2164 (class 0 OID 16456)
-- Dependencies: 197
-- Data for Name: MateriaCurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."MateriaCurso" ("idMateria", "idCurso") FROM stdin;
1	1
2	2
3	3
4	4
\.


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 196
-- Name: MateriaCurso_idCurso_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."MateriaCurso_idCurso_seq"', 1, true);


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 195
-- Name: MateriaCurso_idMateria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."MateriaCurso_idMateria_seq"', 1, false);


--
-- TOC entry 2045 (class 2606 OID 16462)
-- Name: pk_idMateriaCurso; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MateriaCurso"
    ADD CONSTRAINT "pk_idMateriaCurso" PRIMARY KEY ("idMateria", "idCurso");


--
-- TOC entry 2047 (class 2606 OID 16468)
-- Name: fk_idCurso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MateriaCurso"
    ADD CONSTRAINT "fk_idCurso" FOREIGN KEY ("idCurso") REFERENCES public."Curso"("idCurso");


--
-- TOC entry 2046 (class 2606 OID 16463)
-- Name: fk_idMateria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."MateriaCurso"
    ADD CONSTRAINT "fk_idMateria" FOREIGN KEY ("idMateria") REFERENCES public."Materia"("idMateria");


-- Completed on 2018-06-29 12:14:45

--
-- PostgreSQL database dump complete
--

