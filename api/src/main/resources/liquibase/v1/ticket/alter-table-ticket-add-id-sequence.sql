CREATE SEQUENCE ticket_id_seq
INCREMENT 1
START 1;

ALTER TABLE area ALTER COLUMN id SET DEFAULT nextval('ticket_id_seq');