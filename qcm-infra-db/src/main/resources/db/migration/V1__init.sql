CREATE SEQUENCE IF NOT EXISTS account_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS event_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS question_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS questionnaire_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS response_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS tag_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS upload_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS webhook_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE account
(
    id            BIGINT                   NOT NULL,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    email         VARCHAR(255),
    user_name     VARCHAR(255),
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    compagny      VARCHAR(255),
    api_key       VARCHAR(255),
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE category
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    libelle       VARCHAR(255),
    type          VARCHAR(255),
    user_id       VARCHAR(255)             NOT NULL,
    lft           BIGINT                   NOT NULL,
    rgt           BIGINT                   NOT NULL,
    level         INTEGER                  NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE event
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    type          VARCHAR(255),
    origin        UUID                     NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE question
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    mandatory     VARCHAR(255),
    question      VARCHAR(1024)            NOT NULL,
    type          VARCHAR(255),
    category_id   BIGINT,
    status        VARCHAR(255),
    tip           VARCHAR(1024),
    owner_id      BIGINT,
    CONSTRAINT pk_question PRIMARY KEY (id)
);

CREATE TABLE question_tag
(
    question_id BIGINT NOT NULL,
    tag_id      BIGINT NOT NULL,
    CONSTRAINT pk_question_tag PRIMARY KEY (question_id, tag_id)
);

CREATE TABLE questionnaire
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    description   VARCHAR(2000),
    title         VARCHAR(255),
    locale        VARCHAR(255),
    status        VARCHAR(255),
    website       VARCHAR(255),
    published     VARCHAR(255),
    category_id   BIGINT,
    CONSTRAINT pk_questionnaire PRIMARY KEY (id)
);

CREATE TABLE questionnaire_question
(
    questionnaire_id BIGINT  NOT NULL,
    question_id      BIGINT  NOT NULL,
    deleted          BOOLEAN,
    position         INTEGER NOT NULL,
    points           INTEGER NOT NULL,
    CONSTRAINT pk_questionnaire_question PRIMARY KEY (questionnaire_id, question_id)
);

CREATE TABLE questionnaire_tag
(
    questionnaire_id BIGINT NOT NULL,
    tag_id           BIGINT NOT NULL,
    deleted          BOOLEAN,
    CONSTRAINT pk_questionnaire_tag PRIMARY KEY (questionnaire_id, tag_id)
);

CREATE TABLE response
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    response      VARCHAR(32672)           NOT NULL,
    number        BIGINT,
    good          VARCHAR(255),
    question_id   BIGINT,
    CONSTRAINT pk_response PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id            BIGINT                   NOT NULL,
    created_by    VARCHAR(255),
    modified_by   VARCHAR(255),
    created_date  TIMESTAMP with time zone NOT NULL,
    modified_date TIMESTAMP with time zone,
    uuid          UUID                     NOT NULL,
    version       BIGINT,
    libelle       VARCHAR(255),
    publique      BOOLEAN,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE upload
(
    id             BIGINT                   NOT NULL,
    created_by     VARCHAR(255),
    modified_by    VARCHAR(255),
    created_date   TIMESTAMP with time zone NOT NULL,
    modified_date  TIMESTAMP with time zone,
    uuid           UUID                     NOT NULL,
    version        BIGINT,
    file_name      VARCHAR(255),
    path_file_name VARCHAR(255),
    data           OID,
    status         VARCHAR(255),
    content_type   VARCHAR(255),
    libelle        VARCHAR(255),
    type           VARCHAR(255),
    CONSTRAINT pk_upload PRIMARY KEY (id)
);

CREATE TABLE webhooks
(
    id               BIGINT                   NOT NULL,
    created_by       VARCHAR(255),
    modified_by      VARCHAR(255),
    created_date     TIMESTAMP with time zone NOT NULL,
    modified_date    TIMESTAMP with time zone,
    uuid             UUID                     NOT NULL,
    version          BIGINT,
    url              VARCHAR(255),
    content_type     VARCHAR(255)             NOT NULL,
    secret           VARCHAR(255)             NOT NULL,
    owner_id         BIGINT                   NOT NULL,
    default_time_out BIGINT,
    CONSTRAINT pk_webhooks PRIMARY KEY (id)
);

ALTER TABLE question
    ADD CONSTRAINT UK_QTO_UUID UNIQUE (uuid);

ALTER TABLE upload
    ADD CONSTRAINT UK_UPD_UUID UNIQUE (uuid);

CREATE INDEX IDX_CTG_CREATE_BY_IDX ON category (type, created_by);

CREATE INDEX IDX_CTG_UUID_IDX ON category (uuid);

CREATE INDEX IDX_EVT_CREATE_BY_IDX ON event (created_by);

CREATE INDEX IDX_EVT_ORIGIN_IDX ON event (origin);

CREATE INDEX IDX_EVT_UUID_IDX ON event (uuid);

CREATE INDEX IDX_QTE_CREATE_BY_IDX ON questionnaire (created_by);

CREATE INDEX IDX_QTE_UUID_IDX ON questionnaire (uuid);

CREATE INDEX IDX_QTO_CREATE_BY_IDX ON question (created_by);

CREATE INDEX IDX_QTO_UUID_IDX ON question (uuid);

CREATE INDEX IDX_TAG_LIBELLE_IDX ON tag (libelle);

CREATE INDEX IDX_TAG_UUID_IDX ON tag (uuid);

CREATE INDEX IDX_UPD_CREATE_BY ON upload (created_by);

CREATE INDEX IDX_UPD_UUID ON upload (uuid);

CREATE INDEX IDX_USR_EMAIL_IDX ON account (email);

ALTER TABLE questionnaire
    ADD CONSTRAINT FK_QUESTIONNAIRE_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE questionnaire_question
    ADD CONSTRAINT FK_QUESTIONNAIRE_QUESTION_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE questionnaire_question
    ADD CONSTRAINT FK_QUESTIONNAIRE_QUESTION_ON_QUESTIONNAIRE FOREIGN KEY (questionnaire_id) REFERENCES questionnaire (id);

ALTER TABLE questionnaire_tag
    ADD CONSTRAINT FK_QUESTIONNAIRE_TAG_ON_QUESTIONNAIRE FOREIGN KEY (questionnaire_id) REFERENCES questionnaire (id);

ALTER TABLE questionnaire_tag
    ADD CONSTRAINT FK_QUESTIONNAIRE_TAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);

ALTER TABLE question
    ADD CONSTRAINT FK_QUESTION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE question
    ADD CONSTRAINT FK_QUESTION_ON_OWNER FOREIGN KEY (owner_id) REFERENCES account (id);

ALTER TABLE question_tag
    ADD CONSTRAINT FK_QUESTION_TAG_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE question_tag
    ADD CONSTRAINT FK_QUESTION_TAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);

ALTER TABLE response
    ADD CONSTRAINT FK_RESPONSE_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE webhooks
    ADD CONSTRAINT FK_WEBHOOKS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES account (id);
