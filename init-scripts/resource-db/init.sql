CREATE SEQUENCE resource_id_seq;

CREATE TABLE resource (
    id INTEGER PRIMARY KEY DEFAULT nextval('resource_id_seq'),
    content BYTEA NOT NULL
);
