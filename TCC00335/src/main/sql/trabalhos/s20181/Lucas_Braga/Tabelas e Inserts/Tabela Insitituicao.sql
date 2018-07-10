--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 11:56:20

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
-- TOC entry 203 (class 1259 OID 16487)
-- Name: Instituicao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Instituicao" (
    "idInstituicao" integer NOT NULL,
    "nomeInstituicao" character varying(200) NOT NULL,
    "campusInstituicao" character varying(80),
    "idEndereco" integer NOT NULL,
    "idTipo" integer NOT NULL
);


ALTER TABLE public."Instituicao" OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16483)
-- Name: Instituicao_idEndereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Instituicao_idEndereco_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Instituicao_idEndereco_seq" OWNER TO postgres;

--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 201
-- Name: Instituicao_idEndereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Instituicao_idEndereco_seq" OWNED BY public."Instituicao"."idEndereco";


--
-- TOC entry 200 (class 1259 OID 16481)
-- Name: Instituicao_idInstituicao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Instituicao_idInstituicao_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Instituicao_idInstituicao_seq" OWNER TO postgres;

--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 200
-- Name: Instituicao_idInstituicao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Instituicao_idInstituicao_seq" OWNED BY public."Instituicao"."idInstituicao";


--
-- TOC entry 202 (class 1259 OID 16485)
-- Name: Instituicao_idTipo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Instituicao_idTipo_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Instituicao_idTipo_seq" OWNER TO postgres;

--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 202
-- Name: Instituicao_idTipo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Instituicao_idTipo_seq" OWNED BY public."Instituicao"."idTipo";


--
-- TOC entry 2042 (class 2604 OID 16490)
-- Name: idInstituicao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao" ALTER COLUMN "idInstituicao" SET DEFAULT nextval('public."Instituicao_idInstituicao_seq"'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16491)
-- Name: idEndereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao" ALTER COLUMN "idEndereco" SET DEFAULT nextval('public."Instituicao_idEndereco_seq"'::regclass);


--
-- TOC entry 2044 (class 2604 OID 16492)
-- Name: idTipo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao" ALTER COLUMN "idTipo" SET DEFAULT nextval('public."Instituicao_idTipo_seq"'::regclass);


--
-- TOC entry 2166 (class 0 OID 16487)
-- Dependencies: 203
-- Data for Name: Instituicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Instituicao" ("idInstituicao", "nomeInstituicao", "campusInstituicao", "idEndereco", "idTipo") FROM stdin;
1	UFF	Praia Vermelha	1	2
2	UFRJ	Centro	2	2
3	Estácio	Centro	3	1
4	UFRJ	Xerém	4	2
\.


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 201
-- Name: Instituicao_idEndereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Instituicao_idEndereco_seq"', 3, true);


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 200
-- Name: Instituicao_idInstituicao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Instituicao_idInstituicao_seq"', 1, false);


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 202
-- Name: Instituicao_idTipo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Instituicao_idTipo_seq"', 4, true);


--
-- TOC entry 2046 (class 2606 OID 16494)
-- Name: pk_idInstituicao; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao"
    ADD CONSTRAINT "pk_idInstituicao" PRIMARY KEY ("idInstituicao");


--
-- TOC entry 2047 (class 2606 OID 16495)
-- Name: fk_idEndereco; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao"
    ADD CONSTRAINT "fk_idEndereco" FOREIGN KEY ("idEndereco") REFERENCES public."Endereco"("idEndereco");


--
-- TOC entry 2048 (class 2606 OID 16500)
-- Name: fk_idTipo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Instituicao"
    ADD CONSTRAINT "fk_idTipo" FOREIGN KEY ("idTipo") REFERENCES public."Tipo"("idTipo");


-- Completed on 2018-06-29 11:56:20

--
-- PostgreSQL database dump complete
--

