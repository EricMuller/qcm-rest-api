ALTER TABLE question   ADD numero_version INTEGER not null default 1;

ALTER TABLE questionnaire_question    ADD numero_version INTEGER;

update questionnaire_question set numero_version = 1;

ALTER TABLE questionnaire_question  ALTER COLUMN numero_version SET NOT NULL;

CREATE INDEX IDX_QTO_ID_NUM_IDX ON question (id, numero_version);

ALTER TABLE questionnaire_question   DROP CONSTRAINT pk_questionnaire_question ;

ALTER TABLE questionnaire_question  ADD CONSTRAINT pk_questionnaire_question PRIMARY KEY (questionnaire_id, question_id, numero_version);
