--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 11:54:15

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
-- TOC entry 192 (class 1259 OID 16438)
-- Name: Endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Endereco" (
    "idEndereco" integer NOT NULL,
    logradouro character varying(80) NOT NULL,
    numero bigint NOT NULL,
    bairro character varying(80) NOT NULL,
    complemento character varying(80),
    cidade character varying(80) NOT NULL,
    estado character varying(80) NOT NULL
);


ALTER TABLE public."Endereco" OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16436)
-- Name: Endereco_idEndereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Endereco_idEndereco_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Endereco_idEndereco_seq" OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 191
-- Name: Endereco_idEndereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Endereco_idEndereco_seq" OWNED BY public."Endereco"."idEndereco";


--
-- TOC entry 2042 (class 2604 OID 16441)
-- Name: idEndereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Endereco" ALTER COLUMN "idEndereco" SET DEFAULT nextval('public."Endereco_idEndereco_seq"'::regclass);


--
-- TOC entry 2160 (class 0 OID 16438)
-- Dependencies: 192
-- Data for Name: Endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Endereco" ("idEndereco", logradouro, numero, bairro, complemento, cidade, estado) FROM stdin;
1	R. Passo da Pátria	152	São Domingos	152-470	Niterói	Rio de Janeiro
2	R. Moncorvo Filho	8	Centro	\N	Rio de Janeiro	Rio de Janeiro
3	Av. Pres. Vargas	642	Centro	\N	Rio de Janeiro	Rio de Janeiro
4	Estr. de Xerém	27	Xerém	\N	Duque de Caxias	Rio de Janeiro
5	R. Itapeva	112	Jardim Anhangá	\N	Duque de Caxias	Rio de Janeiro
6	R. Santa Clara	567	Copacabana	Apt.	Rio de Janeiro	Rio de Janeiro
7	R. A7	234	Imbarie	\N	Duque de Caxias	Rio de Janeiro
8	R. Neves	564	Realengo	\N	Rio de Janeiro	Rio de Janeiro
\.


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 191
-- Name: Endereco_idEndereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Endereco_idEndereco_seq"', 1, false);


--
-- TOC entry 2044 (class 2606 OID 16443)
-- Name: pk_idEndereco; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Endereco"
    ADD CONSTRAINT "pk_idEndereco" PRIMARY KEY ("idEndereco");


-- Completed on 2018-06-29 11:54:16

--
-- PostgreSQL database dump complete
--

