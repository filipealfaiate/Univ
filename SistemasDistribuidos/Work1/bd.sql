--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.2

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: centros; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.centros (
    nomec character varying(128) NOT NULL
);


ALTER TABLE public.centros OWNER TO "43017";

--
-- Name: efeitos; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.efeitos (
    codigo character varying(4) NOT NULL,
    descricao character varying(500) NOT NULL,
    modelovac character varying(50)
);


ALTER TABLE public.efeitos OWNER TO "43017";

--
-- Name: fila; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.fila (
    nomec character varying(128),
    cc character varying(12),
    codigo character varying(4) NOT NULL
);


ALTER TABLE public.fila OWNER TO "43017";

--
-- Name: pessoa; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.pessoa (
    cc character varying(12) NOT NULL,
    nomep character varying(128),
    idade integer,
    genero character(1)
);


ALTER TABLE public.pessoa OWNER TO "43017";

--
-- Name: vacinados; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.vacinados (
    codigo character varying(4) NOT NULL,
    data timestamp without time zone,
    modelovac character varying(50)
);


ALTER TABLE public.vacinados OWNER TO "43017";

--
-- Data for Name: centros; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.centros (nomec) FROM stdin;
hospital de evora
centro de saude
enfermaria
\.


--
-- Data for Name: efeitos; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.efeitos (codigo, descricao, modelovac) FROM stdin;
\.


--
-- Data for Name: fila; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.fila (nomec, cc, codigo) FROM stdin;
\.


--
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.pessoa (cc, nomep, idade, genero) FROM stdin;
\.


--
-- Data for Name: vacinados; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.vacinados (codigo, data, modelovac) FROM stdin;
\.


--
-- Name: centros centros_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.centros
    ADD CONSTRAINT centros_pkey PRIMARY KEY (nomec);


--
-- Name: efeitos efeitos_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.efeitos
    ADD CONSTRAINT efeitos_pkey PRIMARY KEY (codigo, descricao);


--
-- Name: fila fila_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila
    ADD CONSTRAINT fila_pkey PRIMARY KEY (codigo);


--
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (cc);


--
-- Name: vacinados vacinados_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.vacinados
    ADD CONSTRAINT vacinados_pkey PRIMARY KEY (codigo);


--
-- Name: efeitos efeitos_codigo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.efeitos
    ADD CONSTRAINT efeitos_codigo_fkey FOREIGN KEY (codigo) REFERENCES public.vacinados(codigo);


--
-- Name: fila fila_cc_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila
    ADD CONSTRAINT fila_cc_fkey FOREIGN KEY (cc) REFERENCES public.pessoa(cc);


--
-- Name: fila fila_nomec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila
    ADD CONSTRAINT fila_nomec_fkey FOREIGN KEY (nomec) REFERENCES public.centros(nomec);


--
-- PostgreSQL database dump complete
--

