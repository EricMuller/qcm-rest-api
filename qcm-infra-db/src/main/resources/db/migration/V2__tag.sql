CREATE SEQUENCE IF NOT EXISTS tag_q_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS tag_qcm_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE tag_question
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    libelle       VARCHAR(255),
    CONSTRAINT pk_tag_question PRIMARY KEY (id)
);

CREATE TABLE tag_questionnaire
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    libelle       VARCHAR(255),
    CONSTRAINT pk_tag_questionnaire PRIMARY KEY (id)
);

CREATE INDEX IDX_TAG_QCM_LIBELLE_IDX ON tag_questionnaire (libelle);

CREATE INDEX IDX_TAG_QCM_UUID_IDX ON tag_questionnaire (uuid);

CREATE INDEX IDX_TAG_Q_LIBELLE_IDX ON tag_question (libelle);

CREATE INDEX IDX_TAG_Q_UUID_IDX ON tag_question (uuid);


ALTER TABLE question_tag
DROP
CONSTRAINT fk_question_tag_on_tag;

ALTER TABLE questionnaire_tag
DROP
CONSTRAINT fk_questionnaire_tag_on_tag;

INSERT INTO tag_question(id , created_by,  modified_by, created_date ,  modified_date , uuid  , version  , libelle )
select distinct  id, created_by,  modified_by, created_date,  modified_date, uuid, version, libelle from tag
inner join question_tag on  question_tag.tag_id = tag.id ;

INSERT INTO tag_questionnaire(id , created_by,  modified_by, created_date ,  modified_date , uuid  , version  , libelle )
select distinct  id, created_by, modified_by, created_date,  modified_date, uuid, version, libelle   from tag
inner join questionnaire_tag on  questionnaire_tag.tag_id = tag.id ;

ALTER TABLE questionnaire_tag
    ADD CONSTRAINT FK_QUESTIONNAIRE_TAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag_questionnaire (id);

ALTER TABLE question_tag
    ADD CONSTRAINT FK_QUESTION_TAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag_question (id);

--DROP TABLE tag CASCADE;

--DROP SEQUENCE tag_seq CASCADE;

