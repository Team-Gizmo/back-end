
-- Sequence objects provide the optimal sequencing option, as they are the most efficient and have the best concurrency
-- https://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing#Example_sequence_generator_XML

-- dml
CREATE SEQUENCE DATABASE_RUN_SEQ START WITH 10 INCREMENT BY 1

-- along with
@Id
@SequenceGenerator(name="DATABASE_RUN_GEN", sequenceName="DATABASE_RUN_SEQ")
@GeneratedValue(generator="DATABASE_RUN_GEN")
private Long id;



SELECT MAX(id) FROM service
CREATE SEQUENCE service_id_seq;
ALTER TABLE service ALTER id SET DEFAULT nextval('service_id_seq');
SELECT setval('service_id_seq', 3247);


SELECT MAX(id) FROM incident
CREATE SEQUENCE incident_id_seq;
ALTER TABLE incident ALTER id SET DEFAULT nextval('incident_id_seq');
SELECT setval('incident_id_seq', 3442);


SELECT MAX(id) FROM keyword
CREATE SEQUENCE keyword_id_seq;
ALTER TABLE keyword ALTER id SET DEFAULT nextval('keyword_id_seq');
SELECT setval('keyword_id_seq', 3440);


SELECT MAX(id) FROM audit
CREATE SEQUENCE audit_id_seq;
ALTER TABLE audit ALTER id SET DEFAULT nextval('audit_id_seq');
SELECT setval('audit_id_seq', 2937);


SELECT MAX(id) FROM audit_record
CREATE SEQUENCE audit_record_id_seq;
ALTER TABLE audit_record ALTER id SET DEFAULT nextval('audit_record_id_seq');
SELECT setval('audit_record_id_seq', 3411);


SELECT MAX(id) FROM database
CREATE SEQUENCE database_id_seq;
ALTER TABLE database ALTER id SET DEFAULT nextval('database_id_seq');
SELECT setval('database_id_seq', 1588);


SELECT MAX(id) FROM database_run
CREATE SEQUENCE database_run_id_seq;
ALTER TABLE database_run ALTER id SET DEFAULT nextval('database_run_id_seq');
SELECT setval('database_run_id_seq', 3404);


SELECT MAX(id) FROM db_user
CREATE SEQUENCE db_user_id_seq;
ALTER TABLE db_user ALTER id SET DEFAULT nextval('db_user_id_seq');
SELECT setval('db_user_id_seq', 1814);

SELECT MAX(id) FROM performance_query
CREATE SEQUENCE performance_query_id_seq;
ALTER TABLE performance_query ALTER id SET DEFAULT nextval('performance_query_id_seq');
SELECT setval('performance_query_id_seq',1);
