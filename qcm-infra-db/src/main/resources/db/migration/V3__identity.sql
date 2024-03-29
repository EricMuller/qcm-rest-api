
--ALTER TABLE TAG_QUESTIONNAIRE ALTER COLUMN id    SET DEFAULT nextval('TAG_QUESTIONNAIRE_id_seq'::regclass);

CREATE SEQUENCE QUESTIONNAIRE_ID_SEQ OWNED BY QUESTIONNAIRE.ID;

ALTER TABLE QUESTIONNAIRE ALTER COLUMN ID SET DEFAULT nextval('QUESTIONNAIRE_ID_SEQ');


SELECT setval(pg_get_serial_sequence('QUESTIONNAIRE', 'id'), (select max(ID) from QUESTIONNAIRE));


CREATE SEQUENCE TAG_QUESTIONNAIRE_ID_SEQ OWNED BY TAG_QUESTIONNAIRE.ID;

ALTER TABLE TAG_QUESTIONNAIRE ALTER COLUMN ID SET DEFAULT nextval('TAG_QUESTIONNAIRE_ID_SEQ');

SELECT setval(pg_get_serial_sequence('TAG_QUESTIONNAIRE', 'id'), (select max(ID) from TAG_QUESTIONNAIRE));

----------------

CREATE SEQUENCE QUESTION_ID_SEQ OWNED BY QUESTION.ID;

ALTER TABLE QUESTION ALTER COLUMN ID SET DEFAULT nextval('QUESTION_ID_SEQ');

SELECT setval(pg_get_serial_sequence('QUESTION', 'id'), (select max(ID) from QUESTION));


CREATE SEQUENCE TAG_QUESTION_ID_SEQ OWNED BY TAG_QUESTION.ID;

ALTER TABLE TAG_QUESTION ALTER COLUMN ID SET DEFAULT nextval('TAG_QUESTION_ID_SEQ');

SELECT setval(pg_get_serial_sequence('TAG_QUESTION', 'id'), (select max(ID) from TAG_QUESTION));

----------------

CREATE SEQUENCE UPLOAD_ID_SEQ OWNED BY UPLOAD.ID;

ALTER TABLE UPLOAD ALTER COLUMN ID SET DEFAULT nextval('UPLOAD_ID_SEQ');

SELECT setval(pg_get_serial_sequence('UPLOAD', 'id'), (select max(ID) from UPLOAD));

--------------

CREATE SEQUENCE ACCOUNT_ID_SEQ OWNED BY ACCOUNT.ID;

ALTER TABLE ACCOUNT ALTER COLUMN ID SET DEFAULT nextval('ACCOUNT_ID_SEQ');

SELECT setval(pg_get_serial_sequence('ACCOUNT', 'id'), (select max(ID) from ACCOUNT));

--------------

CREATE SEQUENCE EVENT_ID_SEQ OWNED BY EVENT.ID;

ALTER TABLE EVENT ALTER COLUMN ID SET DEFAULT nextval('EVENT_ID_SEQ');

SELECT setval(pg_get_serial_sequence('EVENT', 'id'), (select max(ID) from EVENT));

--------------

CREATE SEQUENCE WEBHOOKS_ID_SEQ OWNED BY WEBHOOKS.ID;

ALTER TABLE WEBHOOKS ALTER COLUMN ID SET DEFAULT nextval('WEBHOOKS_ID_SEQ');

SELECT setval(pg_get_serial_sequence('WEBHOOKS', 'id'), (select max(ID) from WEBHOOKS));

--------------

CREATE SEQUENCE CATEGORY_ID_SEQ OWNED BY CATEGORY.ID;

ALTER TABLE CATEGORY ALTER COLUMN ID SET DEFAULT nextval('CATEGORY_ID_SEQ');

SELECT setval(pg_get_serial_sequence('CATEGORY', 'id'), (select max(ID) from CATEGORY));
--------------

CREATE SEQUENCE RESPONSE_ID_SEQ OWNED BY RESPONSE.ID;

ALTER TABLE RESPONSE ALTER COLUMN ID SET DEFAULT nextval('RESPONSE_ID_SEQ');

SELECT setval(pg_get_serial_sequence('RESPONSE', 'id'), (select max(ID) from RESPONSE));
