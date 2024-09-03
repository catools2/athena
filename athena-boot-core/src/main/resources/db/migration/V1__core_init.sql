CREATE SCHEMA athena_core;

CREATE TABLE athena_core.app_version (id bigserial NOT NULL,
project_id bigint NOT NULL,
code varchar(10) NOT NULL UNIQUE,
name varchar(50) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE athena_core.environment (id bigserial NOT NULL,
project_id bigint NOT NULL,
code varchar(10) NOT NULL UNIQUE,
name varchar(50) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE athena_core.project (id bigserial NOT NULL,
code varchar(10) NOT NULL UNIQUE,
name varchar(50) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE athena_core.user (id bigserial NOT NULL,
username varchar(150) NOT NULL UNIQUE,
PRIMARY KEY (id));

CREATE TABLE athena_core.user_alias (id bigserial NOT NULL,
user_id bigint NOT NULL,
alias varchar(200) NOT NULL UNIQUE,
PRIMARY KEY (id));

CREATE INDEX idx_athena_core_app_version_code ON athena_core.app_version (code);
CREATE INDEX idx_athena_core_environment_code ON athena_core.environment (code);
CREATE INDEX idx_athena_core_project_code ON athena_core.project (code);

ALTER TABLE IF EXISTS athena_core.app_version
ADD CONSTRAINT fk_athena_core_app_version_project_id_to_project FOREIGN KEY (project_id) REFERENCES athena_core.project;

ALTER TABLE IF EXISTS athena_core.environment
ADD CONSTRAINT fk_athena_core_environment_project_id_to_project FOREIGN KEY (project_id) REFERENCES athena_core.project;

ALTER TABLE IF EXISTS athena_core.user_alias
ADD CONSTRAINT fk_athena_core_user_alias_user_id_to_user FOREIGN KEY (user_id) REFERENCES athena_core.user;
