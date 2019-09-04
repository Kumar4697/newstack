-- Table: public.department

-- DROP TABLE public.department;

CREATE TABLE IF NOT EXISTS public.department
(
    id bigint NOT NULL,
    name character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT department_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.department
    OWNER to postgres;
    

-- Table: public.employees

-- DROP TABLE public.employees;

CREATE TABLE IF NOT EXISTS public.employees
(
    id bigint NOT NULL,
    email_address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    dept_id bigint NOT NULL,
    CONSTRAINT employees_pkey PRIMARY KEY (id),
    CONSTRAINT department_foreignkey FOREIGN KEY (dept_id)
        REFERENCES public.department (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.employees
    OWNER to postgres;