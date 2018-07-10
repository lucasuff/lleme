--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 11:57:06

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
-- TOC entry 194 (class 1259 OID 16446)
-- Name: Materia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Materia" (
    "idMateria" integer NOT NULL,
    "nomeMateria" character varying(80) NOT NULL,
    semestre integer NOT NULL,
    ano integer NOT NULL,
    qtdprovas integer NOT NULL
);


ALTER TABLE public."Materia" OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 16444)
-- Name: Materia_idMateria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Materia_idMateria_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Materia_idMateria_seq" OWNER TO postgres;

--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 193
-- Name: Materia_idMateria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Materia_idMateria_seq" OWNED BY public."Materia"."idMateria";


--
-- TOC entry 2042 (class 2604 OID 16449)
-- Name: idMateria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Materia" ALTER COLUMN "idMateria" SET DEFAULT nextval('public."Materia_idMateria_seq"'::regclass);


--
-- TOC entry 2161 (class 0 OID 16446)
-- Dependencies: 194
-- Data for Name: Materia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Materia" ("idMateria", "nomeMateria", semestre, ano, qtdprovas) FROM stdin;
1	Banco de Dados	1	2018	3
2	Estrutura de Dados	1	2018	4
3	Propriedade Intelectual	2	2017	2
4	Programação I	1	2017	2
\.


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 193
-- Name: Materia_idMateria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Materia_idMateria_seq"', 1, false);


--
-- TOC entry 2044 (class 2606 OID 16451)
-- Name: idMateria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Materia"
    ADD CONSTRAINT "idMateria" PRIMARY KEY ("idMateria");


--
-- TOC entry 2045 (class 2620 OID 16556)
-- Name: altera_qtd_provas; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER altera_qtd_provas BEFORE UPDATE ON public."Materia" FOR EACH ROW EXECUTE PROCEDURE public.valida_alt_qtd_provas();


-- Completed on 2018-06-29 11:57:07

--
-- PostgreSQL database dump complete
--

