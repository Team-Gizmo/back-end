DROP TABLE assignment_group_keyword;

DROP TABLE incident_keyword;

DROP TABLE incident;

DROP TABLE keyword;

DROP TABLE assignment_group;

DROP TABLE incident_history;

DROP TABLE keyword_history;

DROP TABLE assignment_group_history;

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

CREATE TABLE assignment_group (
   id bigint PRIMARY KEY NOT NULL,
   create_date timestamp NOT NULL,
   name varchar(255) NOT NULL
);

ALTER TABLE assignment_group ADD CONSTRAINT assignment_group_name_constraint UNIQUE (name);

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

CREATE TABLE assignment_group_keyword (
   assignment_group_id bigint NOT NULL,
   keyword_id bigint NOT NULL,
   CONSTRAINT assignment_group_keyword_pkey PRIMARY KEY (assignment_group_id, keyword_id)
);

ALTER TABLE assignment_group_keyword
ADD CONSTRAINT assignment_group_group
FOREIGN KEY (assignment_group_id)
REFERENCES assignment_group(id);

ALTER TABLE assignment_group_keyword
ADD CONSTRAINT assignment_group_keyword
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

CREATE TABLE assignment_group_history (
   id bigint PRIMARY KEY NOT NULL,
   keyword_id bigint NOT NULL,
   action int NOT NULL,
   modified_date timestamp NOT NULL
);