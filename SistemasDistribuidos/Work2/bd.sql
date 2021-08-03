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
    nomecentro character varying(50) NOT NULL,
    nmaxvac integer
);


ALTER TABLE public.centros OWNER TO "43017";

--
-- Name: cidadao; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.cidadao (
    nome character varying(50),
    idade integer,
    email character varying(50),
    codigo character varying(10) NOT NULL
);


ALTER TABLE public.cidadao OWNER TO "43017";

--
-- Name: fila_vacinacao; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.fila_vacinacao (
    codigo character varying(10) NOT NULL,
    nomecentro character varying(50),
    data date
);


ALTER TABLE public.fila_vacinacao OWNER TO "43017";

--
-- Name: vacinado; Type: TABLE; Schema: public; Owner: 43017
--

CREATE TABLE public.vacinado (
    codigo character varying(10) NOT NULL,
    nomecentro character varying(50),
    data date
);


ALTER TABLE public.vacinado OWNER TO "43017";

--
-- Data for Name: centros; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.centros (nomecentro, nmaxvac) FROM stdin;
\.


--
-- Data for Name: cidadao; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.cidadao (nome, idade, email, codigo) FROM stdin;
\.


--
-- Data for Name: fila_vacinacao; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.fila_vacinacao (codigo, nomecentro, data) FROM stdin;
\.


--
-- Data for Name: vacinado; Type: TABLE DATA; Schema: public; Owner: 43017
--

COPY public.vacinado (codigo, nomecentro, data) FROM stdin;
\.


--
-- Name: centros centros_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.centros
    ADD CONSTRAINT centros_pkey PRIMARY KEY (nomecentro);


--
-- Name: cidadao cidadao_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.cidadao
    ADD CONSTRAINT cidadao_pkey PRIMARY KEY (codigo);


--
-- Name: fila_vacinacao fila_vacinacao_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila_vacinacao
    ADD CONSTRAINT fila_vacinacao_pkey PRIMARY KEY (codigo);


--
-- Name: vacinado vacinado_pkey; Type: CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.vacinado
    ADD CONSTRAINT vacinado_pkey PRIMARY KEY (codigo);


--
-- Name: fila_vacinacao fila_vacinacao_codigo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila_vacinacao
    ADD CONSTRAINT fila_vacinacao_codigo_fkey FOREIGN KEY (codigo) REFERENCES public.cidadao(codigo);


--
-- Name: fila_vacinacao fila_vacinacao_nomecentro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.fila_vacinacao
    ADD CONSTRAINT fila_vacinacao_nomecentro_fkey FOREIGN KEY (nomecentro) REFERENCES public.centros(nomecentro);


--
-- Name: vacinado vacinado_codigo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.vacinado
    ADD CONSTRAINT vacinado_codigo_fkey FOREIGN KEY (codigo) REFERENCES public.cidadao(codigo);


--
-- Name: vacinado vacinado_nomecentro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: 43017
--

ALTER TABLE ONLY public.vacinado
    ADD CONSTRAINT vacinado_nomecentro_fkey FOREIGN KEY (nomecentro) REFERENCES public.centros(nomecentro);


--
-- PostgreSQL database dump complete
--

