--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.13
-- Dumped by pg_dump version 9.5.13

-- Started on 2018-06-29 12:00:12

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
-- TOC entry 190 (class 1259 OID 16423)
-- Name: Prova; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Prova" (
    "idProva" integer NOT NULL,
    "arquivoProva" character varying(80) NOT NULL,
    "arquivoGabarito" character varying(80),
    "idEstudante" integer NOT NULL,
    "idMateria" integer NOT NULL
);


ALTER TABLE public."Prova" OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16419)
-- Name: Prova_idEstudante_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Prova_idEstudante_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Prova_idEstudante_seq" OWNER TO postgres;

--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 188
-- Name: Prova_idEstudante_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Prova_idEstudante_seq" OWNED BY public."Prova"."idEstudante";


--
-- TOC entry 189 (class 1259 OID 16421)
-- Name: Prova_idMateria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Prova_idMateria_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Prova_idMateria_seq" OWNER TO postgres;

--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 189
-- Name: Prova_idMateria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Prova_idMateria_seq" OWNED BY public."Prova"."idMateria";


--
-- TOC entry 187 (class 1259 OID 16417)
-- Name: Prova_idProva_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Prova_idProva_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Prova_idProva_seq" OWNER TO postgres;

--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 187
-- Name: Prova_idProva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Prova_idProva_seq" OWNED BY public."Prova"."idProva";


--
-- TOC entry 2042 (class 2604 OID 16426)
-- Name: idProva; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova" ALTER COLUMN "idProva" SET DEFAULT nextval('public."Prova_idProva_seq"'::regclass);


--
-- TOC entry 2043 (class 2604 OID 16427)
-- Name: idEstudante; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova" ALTER COLUMN "idEstudante" SET DEFAULT nextval('public."Prova_idEstudante_seq"'::regclass);


--
-- TOC entry 2044 (class 2604 OID 16428)
-- Name: idMateria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova" ALTER COLUMN "idMateria" SET DEFAULT nextval('public."Prova_idMateria_seq"'::regclass);


--
-- TOC entry 2168 (class 0 OID 16423)
-- Dependencies: 190
-- Data for Name: Prova; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Prova" ("idProva", "arquivoProva", "arquivoGabarito", "idEstudante", "idMateria") FROM stdin;
1	prova1.pdf	gabarito1.pdf	1	1
2	prova2.pdf	gabarito2.pdf	1	1
3	prova1.pdf	\N	2	2
4	prova1.pdf	gabarito1.pdf	3	3
5	prova1.pdf	gabarito1.pdf	4	4
39	teste	teste	4	4
\.


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 188
-- Name: Prova_idEstudante_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Prova_idEstudante_seq"', 1, false);


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 189
-- Name: Prova_idMateria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Prova_idMateria_seq"', 1, false);


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 187
-- Name: Prova_idProva_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Prova_idProva_seq"', 42, true);


--
-- TOC entry 2046 (class 2606 OID 16430)
-- Name: pk_idProva; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova"
    ADD CONSTRAINT "pk_idProva" PRIMARY KEY ("idProva");


--
-- TOC entry 2049 (class 2620 OID 16551)
-- Name: verifica_curso; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER verifica_curso BEFORE INSERT ON public."Prova" FOR EACH ROW EXECUTE PROCEDURE public.valida_upload();


--
-- TOC entry 2050 (class 2620 OID 16554)
-- Name: verifica_quantidade; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER verifica_quantidade BEFORE INSERT ON public."Prova" FOR EACH ROW EXECUTE PROCEDURE public.valida_quantidade();


--
-- TOC entry 2047 (class 2606 OID 16431)
-- Name: fk_idEstudante; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova"
    ADD CONSTRAINT "fk_idEstudante" FOREIGN KEY ("idEstudante") REFERENCES public."Estudante"("idEstudante");


--
-- TOC entry 2048 (class 2606 OID 16537)
-- Name: fk_idMateria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prova"
    ADD CONSTRAINT "fk_idMateria" FOREIGN KEY ("idMateria") REFERENCES public."Materia"("idMateria");


-- Completed on 2018-06-29 12:00:12

--
-- PostgreSQL database dump complete
--

