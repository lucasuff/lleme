--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 12:11:03

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
-- TOC entry 184 (class 1259 OID 16401)
-- Name: Estudante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Estudante" (
    cpf character varying(11),
    "nomeEstudante" character varying(80),
    "idCurso" integer NOT NULL,
    "idEndereco" integer NOT NULL,
    "idEstudante" integer NOT NULL
);


ALTER TABLE public."Estudante" OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16395)
-- Name: Estudante_idCurso_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Estudante_idCurso_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Estudante_idCurso_seq" OWNER TO postgres;

--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 181
-- Name: Estudante_idCurso_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Estudante_idCurso_seq" OWNED BY public."Estudante"."idCurso";


--
-- TOC entry 182 (class 1259 OID 16397)
-- Name: Estudante_idEndereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Estudante_idEndereco_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Estudante_idEndereco_seq" OWNER TO postgres;

--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 182
-- Name: Estudante_idEndereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Estudante_idEndereco_seq" OWNED BY public."Estudante"."idEndereco";


--
-- TOC entry 183 (class 1259 OID 16399)
-- Name: Estudante_idEstudante_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Estudante_idEstudante_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Estudante_idEstudante_seq" OWNER TO postgres;

--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 183
-- Name: Estudante_idEstudante_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Estudante_idEstudante_seq" OWNED BY public."Estudante"."idEstudante";


--
-- TOC entry 2042 (class 2604 OID 16404)
-- Name: idCurso; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante" ALTER COLUMN "idCurso" SET DEFAULT nextval('public."Estudante_idCurso_seq"'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16405)
-- Name: idEndereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante" ALTER COLUMN "idEndereco" SET DEFAULT nextval('public."Estudante_idEndereco_seq"'::regclass);


--
-- TOC entry 2044 (class 2604 OID 16406)
-- Name: idEstudante; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante" ALTER COLUMN "idEstudante" SET DEFAULT nextval('public."Estudante_idEstudante_seq"'::regclass);


--
-- TOC entry 2167 (class 0 OID 16401)
-- Dependencies: 184
-- Data for Name: Estudante; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Estudante" (cpf, "nomeEstudante", "idCurso", "idEndereco", "idEstudante") FROM stdin;
15535388700	Lucas Braga Mendonça	1	5	1
11111111111	Fabio Silva Junior	2	6	2
89712902712	Cleyton Braga Silva	3	7	3
97093273927	Nataly Santos Teteo	4	8	4
15535388700	Lucas Braga Mendonça	2	5	59
\.


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 181
-- Name: Estudante_idCurso_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Estudante_idCurso_seq"', 4, true);


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 182
-- Name: Estudante_idEndereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Estudante_idEndereco_seq"', 4, true);


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 183
-- Name: Estudante_idEstudante_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Estudante_idEstudante_seq"', 61, true);


--
-- TOC entry 2046 (class 2606 OID 16408)
-- Name: pk_idEstudante; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante"
    ADD CONSTRAINT "pk_idEstudante" PRIMARY KEY ("idEstudante");


--
-- TOC entry 2049 (class 2620 OID 16577)
-- Name: insere_estudante; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insere_estudante BEFORE INSERT ON public."Estudante" FOR EACH ROW EXECUTE PROCEDURE public.valida_cadastro();


--
-- TOC entry 2048 (class 2606 OID 16532)
-- Name: fk_idCurso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante"
    ADD CONSTRAINT "fk_idCurso" FOREIGN KEY ("idCurso") REFERENCES public."Curso"("idCurso");


--
-- TOC entry 2047 (class 2606 OID 16527)
-- Name: fk_idEndereco; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Estudante"
    ADD CONSTRAINT "fk_idEndereco" FOREIGN KEY ("idEndereco") REFERENCES public."Endereco"("idEndereco");


-- Completed on 2018-06-29 12:11:04

--
-- PostgreSQL database dump complete
--

