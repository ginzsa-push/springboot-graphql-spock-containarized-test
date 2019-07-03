--
-- Name: update table item
--
ALTER TABLE public.item
    ADD COLUMN msg integer NOT NULL,
    ADD COLUMN language character varying(255);