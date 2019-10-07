DROP TABLE incident_keyword;

DROP TABLE incident;

DROP TABLE keyword;

DROP TABLE incident_history;

DROP TABLE keyword_history;

CREATE TABLE incident (
   id bigint PRIMARY KEY NOT NULL,
   create_date timestamp NOT NULL,
   description varchar(255) NOT NULL,
   name varchar(255) NOT NULL,
   resolve_date timestamp,
   solution text,
   optlock int DEFAULT 0 NOT NULL
);

ALTER TABLE incident ADD CONSTRAINT incident_name_constraint UNIQUE (name);

CREATE TABLE keyword (
   id bigint PRIMARY KEY NOT NULL,
   create_date timestamp NOT NULL,
   name varchar(255) NOT NULL
);

ALTER TABLE keyword ADD CONSTRAINT keyword_name_constraint UNIQUE (name);

CREATE TABLE incident_keyword (
   incident_id bigint NOT NULL,
   keyword_id bigint NOT NULL,
   CONSTRAINT incident_keyword_pkey PRIMARY KEY (incident_id, keyword_id)
);

ALTER TABLE incident_keyword
ADD CONSTRAINT incident_keyword_incident
FOREIGN KEY (incident_id)
REFERENCES incident(id);

ALTER TABLE incident_keyword
ADD CONSTRAINT incident_keyword_keyword
FOREIGN KEY (keyword_id)
REFERENCES keyword(id);

CREATE TABLE incident_history (
   id bigint PRIMARY KEY NOT NULL,
   incident_id bigint NOT NULL,
   action int NOT NULL,
   modified_date timestamp NOT NULL
);

CREATE TABLE keyword_history (
   id bigint PRIMARY KEY NOT NULL,
   keyword_id bigint NOT NULL,
   action int NOT NULL,
   modified_date timestamp NOT NULL
);
