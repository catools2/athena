--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: athena_core; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_core;


ALTER SCHEMA athena_core OWNER TO postgres;

--
-- Name: athena_git; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_git;


ALTER SCHEMA athena_git OWNER TO postgres;

--
-- Name: athena_kube; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_kube;


ALTER SCHEMA athena_kube OWNER TO postgres;

--
-- Name: athena_metric; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_metric;


ALTER SCHEMA athena_metric OWNER TO postgres;

--
-- Name: athena_openapi; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_openapi;


ALTER SCHEMA athena_openapi OWNER TO postgres;

--
-- Name: athena_pipeline; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_pipeline;


ALTER SCHEMA athena_pipeline OWNER TO postgres;

--
-- Name: athena_tms; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA athena_tms;


ALTER SCHEMA athena_tms OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: app_version; Type: TABLE; Schema: athena_core; Owner: postgres
--

CREATE TABLE athena_core.app_version (
    id bigint NOT NULL,
    project_id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_core.app_version OWNER TO postgres;

--
-- Name: app_version_id_seq; Type: SEQUENCE; Schema: athena_core; Owner: postgres
--

CREATE SEQUENCE athena_core.app_version_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_core.app_version_id_seq OWNER TO postgres;

--
-- Name: app_version_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_core; Owner: postgres
--

ALTER SEQUENCE athena_core.app_version_id_seq OWNED BY athena_core.app_version.id;


--
-- Name: environment; Type: TABLE; Schema: athena_core; Owner: postgres
--

CREATE TABLE athena_core.environment (
    id bigint NOT NULL,
    project_id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_core.environment OWNER TO postgres;

--
-- Name: environment_id_seq; Type: SEQUENCE; Schema: athena_core; Owner: postgres
--

CREATE SEQUENCE athena_core.environment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_core.environment_id_seq OWNER TO postgres;

--
-- Name: environment_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_core; Owner: postgres
--

ALTER SEQUENCE athena_core.environment_id_seq OWNED BY athena_core.environment.id;


--
-- Name: project; Type: TABLE; Schema: athena_core; Owner: postgres
--

CREATE TABLE athena_core.project (
    id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_core.project OWNER TO postgres;

--
-- Name: project_id_seq; Type: SEQUENCE; Schema: athena_core; Owner: postgres
--

CREATE SEQUENCE athena_core.project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_core.project_id_seq OWNER TO postgres;

--
-- Name: project_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_core; Owner: postgres
--

ALTER SEQUENCE athena_core.project_id_seq OWNED BY athena_core.project.id;


--
-- Name: user; Type: TABLE; Schema: athena_core; Owner: postgres
--

CREATE TABLE athena_core."user" (
    id bigint NOT NULL,
    username character varying(150) NOT NULL
);


ALTER TABLE athena_core."user" OWNER TO postgres;

--
-- Name: user_alias; Type: TABLE; Schema: athena_core; Owner: postgres
--

CREATE TABLE athena_core.user_alias (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    alias character varying(200) NOT NULL
);


ALTER TABLE athena_core.user_alias OWNER TO postgres;

--
-- Name: user_alias_id_seq; Type: SEQUENCE; Schema: athena_core; Owner: postgres
--

CREATE SEQUENCE athena_core.user_alias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_core.user_alias_id_seq OWNER TO postgres;

--
-- Name: user_alias_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_core; Owner: postgres
--

ALTER SEQUENCE athena_core.user_alias_id_seq OWNED BY athena_core.user_alias.id;


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: athena_core; Owner: postgres
--

CREATE SEQUENCE athena_core.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_core.user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_core; Owner: postgres
--

ALTER SEQUENCE athena_core.user_id_seq OWNED BY athena_core."user".id;


--
-- Name: commit; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.commit (
    line_deleted integer,
    line_inserted integer,
    parent_count integer NOT NULL,
    total_file integer,
    author_id bigint NOT NULL,
    commit_time timestamp with time zone NOT NULL,
    committer_id bigint NOT NULL,
    id bigint NOT NULL,
    repository_id bigint NOT NULL,
    hash character varying(50) NOT NULL,
    parent_hash character varying(50),
    short_message character varying(5000) NOT NULL
);


ALTER TABLE athena_git.commit OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE; Schema: athena_git; Owner: postgres
--

CREATE SEQUENCE athena_git.commit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_git.commit_id_seq OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_git; Owner: postgres
--

ALTER SEQUENCE athena_git.commit_id_seq OWNED BY athena_git.commit.id;


--
-- Name: commit_metadata; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.commit_metadata (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_git.commit_metadata OWNER TO postgres;

--
-- Name: commit_metadata_id_seq; Type: SEQUENCE; Schema: athena_git; Owner: postgres
--

CREATE SEQUENCE athena_git.commit_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_git.commit_metadata_id_seq OWNER TO postgres;

--
-- Name: commit_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_git; Owner: postgres
--

ALTER SEQUENCE athena_git.commit_metadata_id_seq OWNED BY athena_git.commit_metadata.id;


--
-- Name: commit_metadata_mid; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.commit_metadata_mid (
    commit_id bigint NOT NULL,
    metadata_id bigint NOT NULL
);


ALTER TABLE athena_git.commit_metadata_mid OWNER TO postgres;

--
-- Name: commit_tag_mid; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.commit_tag_mid (
    commit_id bigint NOT NULL,
    tag_id bigint NOT NULL
);


ALTER TABLE athena_git.commit_tag_mid OWNER TO postgres;

--
-- Name: diff_entry; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.diff_entry (
    deleted integer NOT NULL,
    inserted integer NOT NULL,
    commit_id bigint NOT NULL,
    id bigint NOT NULL,
    change_type character varying(30) NOT NULL,
    new character varying(1000) NOT NULL,
    old character varying(1000) NOT NULL
);


ALTER TABLE athena_git.diff_entry OWNER TO postgres;

--
-- Name: diff_entry_id_seq; Type: SEQUENCE; Schema: athena_git; Owner: postgres
--

CREATE SEQUENCE athena_git.diff_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_git.diff_entry_id_seq OWNER TO postgres;

--
-- Name: diff_entry_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_git; Owner: postgres
--

ALTER SEQUENCE athena_git.diff_entry_id_seq OWNED BY athena_git.diff_entry.id;


--
-- Name: repository; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.repository (
    id bigint NOT NULL,
    last_sync timestamp with time zone,
    name character varying(200) NOT NULL,
    url character varying(300) NOT NULL
);


ALTER TABLE athena_git.repository OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE; Schema: athena_git; Owner: postgres
--

CREATE SEQUENCE athena_git.repository_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_git.repository_id_seq OWNER TO postgres;

--
-- Name: repository_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_git; Owner: postgres
--

ALTER SEQUENCE athena_git.repository_id_seq OWNED BY athena_git.repository.id;


--
-- Name: tag; Type: TABLE; Schema: athena_git; Owner: postgres
--

CREATE TABLE athena_git.tag (
    id bigint NOT NULL,
    hash character varying(50) NOT NULL,
    name character varying(200) NOT NULL
);


ALTER TABLE athena_git.tag OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE; Schema: athena_git; Owner: postgres
--

CREATE SEQUENCE athena_git.tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_git.tag_id_seq OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_git; Owner: postgres
--

ALTER SEQUENCE athena_git.tag_id_seq OWNED BY athena_git.tag.id;


--
-- Name: container; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.container (
    ready boolean,
    restart_count integer,
    started boolean,
    id bigint NOT NULL,
    last_sync timestamp with time zone NOT NULL,
    pod_id bigint NOT NULL,
    started_at timestamp with time zone,
    type character varying(100) NOT NULL,
    image_id character varying(300) NOT NULL,
    name character varying(300) NOT NULL,
    image character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.container OWNER TO postgres;

--
-- Name: container_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.container_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.container_id_seq OWNER TO postgres;

--
-- Name: container_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.container_id_seq OWNED BY athena_kube.container.id;


--
-- Name: container_metadata; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.container_metadata (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.container_metadata OWNER TO postgres;

--
-- Name: container_metadata_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.container_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.container_metadata_id_seq OWNER TO postgres;

--
-- Name: container_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.container_metadata_id_seq OWNED BY athena_kube.container_metadata.id;


--
-- Name: container_metadata_mid; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.container_metadata_mid (
    container_id bigint NOT NULL,
    metadata_id bigint NOT NULL
);


ALTER TABLE athena_kube.container_metadata_mid OWNER TO postgres;

--
-- Name: pod; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod (
    created_at timestamp with time zone,
    deleted_at timestamp with time zone,
    id bigint NOT NULL,
    last_sync timestamp with time zone NOT NULL,
    project_id bigint NOT NULL,
    status_id bigint NOT NULL,
    uid character varying(36),
    namespace character varying(100),
    hostname character varying(200),
    node_name character varying(200),
    name character varying(500)
);


ALTER TABLE athena_kube.pod OWNER TO postgres;

--
-- Name: pod_annotation; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_annotation (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.pod_annotation OWNER TO postgres;

--
-- Name: pod_annotation_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_annotation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_annotation_id_seq OWNER TO postgres;

--
-- Name: pod_annotation_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_annotation_id_seq OWNED BY athena_kube.pod_annotation.id;


--
-- Name: pod_annotation_mid; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_annotation_mid (
    annotation_id bigint NOT NULL,
    pod_id bigint NOT NULL
);


ALTER TABLE athena_kube.pod_annotation_mid OWNER TO postgres;

--
-- Name: pod_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_id_seq OWNER TO postgres;

--
-- Name: pod_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_id_seq OWNED BY athena_kube.pod.id;


--
-- Name: pod_label; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_label (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.pod_label OWNER TO postgres;

--
-- Name: pod_label_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_label_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_label_id_seq OWNER TO postgres;

--
-- Name: pod_label_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_label_id_seq OWNED BY athena_kube.pod_label.id;


--
-- Name: pod_label_mid; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_label_mid (
    label_id bigint NOT NULL,
    pod_id bigint NOT NULL
);


ALTER TABLE athena_kube.pod_label_mid OWNER TO postgres;

--
-- Name: pod_metadata; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_metadata (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.pod_metadata OWNER TO postgres;

--
-- Name: pod_metadata_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_metadata_id_seq OWNER TO postgres;

--
-- Name: pod_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_metadata_id_seq OWNED BY athena_kube.pod_metadata.id;


--
-- Name: pod_metadata_mid; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_metadata_mid (
    metadata_id bigint NOT NULL,
    pod_id bigint NOT NULL
);


ALTER TABLE athena_kube.pod_metadata_mid OWNER TO postgres;

--
-- Name: pod_selector; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_selector (
    id bigint NOT NULL,
    name character varying(300) NOT NULL,
    value character varying(1000) NOT NULL
);


ALTER TABLE athena_kube.pod_selector OWNER TO postgres;

--
-- Name: pod_selector_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_selector_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_selector_id_seq OWNER TO postgres;

--
-- Name: pod_selector_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_selector_id_seq OWNED BY athena_kube.pod_selector.id;


--
-- Name: pod_selector_mid; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_selector_mid (
    pod_id bigint NOT NULL,
    selector_id bigint NOT NULL
);


ALTER TABLE athena_kube.pod_selector_mid OWNER TO postgres;

--
-- Name: pod_status; Type: TABLE; Schema: athena_kube; Owner: postgres
--

CREATE TABLE athena_kube.pod_status (
    id bigint NOT NULL,
    name character varying(200),
    phase character varying(200),
    message character varying(1000),
    reason character varying(1000)
);


ALTER TABLE athena_kube.pod_status OWNER TO postgres;

--
-- Name: pod_status_id_seq; Type: SEQUENCE; Schema: athena_kube; Owner: postgres
--

CREATE SEQUENCE athena_kube.pod_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_kube.pod_status_id_seq OWNER TO postgres;

--
-- Name: pod_status_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_kube; Owner: postgres
--

ALTER SEQUENCE athena_kube.pod_status_id_seq OWNED BY athena_kube.pod_status.id;


--
-- Name: action; Type: TABLE; Schema: athena_metric; Owner: postgres
--

CREATE TABLE athena_metric.action (
    id bigint NOT NULL,
    category character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    type character varying(100) NOT NULL,
    target character varying(1000) NOT NULL,
    command character varying(5000) NOT NULL,
    parameter character varying(5000)
);


ALTER TABLE athena_metric.action OWNER TO postgres;

--
-- Name: action_id_seq; Type: SEQUENCE; Schema: athena_metric; Owner: postgres
--

CREATE SEQUENCE athena_metric.action_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_metric.action_id_seq OWNER TO postgres;

--
-- Name: action_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_metric; Owner: postgres
--

ALTER SEQUENCE athena_metric.action_id_seq OWNED BY athena_metric.action.id;


--
-- Name: metric; Type: TABLE; Schema: athena_metric; Owner: postgres
--

CREATE TABLE athena_metric.metric (
    action_id bigint NOT NULL,
    action_time timestamp with time zone NOT NULL,
    duration bigint,
    environment_id bigint NOT NULL,
    id bigint NOT NULL,
    project_id bigint NOT NULL
);


ALTER TABLE athena_metric.metric OWNER TO postgres;

--
-- Name: metric_id_seq; Type: SEQUENCE; Schema: athena_metric; Owner: postgres
--

CREATE SEQUENCE athena_metric.metric_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_metric.metric_id_seq OWNER TO postgres;

--
-- Name: metric_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_metric; Owner: postgres
--

ALTER SEQUENCE athena_metric.metric_id_seq OWNED BY athena_metric.metric.id;


--
-- Name: api_path; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.api_path (
    first_time_seen timestamp with time zone,
    id bigint NOT NULL,
    last_sync_time timestamp with time zone,
    spec_id bigint NOT NULL,
    method character varying(10) NOT NULL,
    url character varying(500) NOT NULL,
    title character varying(1000),
    description character varying(5000),
    parameters jsonb
);


ALTER TABLE athena_openapi.api_path OWNER TO postgres;

--
-- Name: api_path_id_seq; Type: SEQUENCE; Schema: athena_openapi; Owner: postgres
--

CREATE SEQUENCE athena_openapi.api_path_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_openapi.api_path_id_seq OWNER TO postgres;

--
-- Name: api_path_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_openapi; Owner: postgres
--

ALTER SEQUENCE athena_openapi.api_path_id_seq OWNED BY athena_openapi.api_path.id;


--
-- Name: api_path_metadata; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.api_path_metadata (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(2000) NOT NULL
);


ALTER TABLE athena_openapi.api_path_metadata OWNER TO postgres;

--
-- Name: api_path_metadata_id_seq; Type: SEQUENCE; Schema: athena_openapi; Owner: postgres
--

CREATE SEQUENCE athena_openapi.api_path_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_openapi.api_path_metadata_id_seq OWNER TO postgres;

--
-- Name: api_path_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_openapi; Owner: postgres
--

ALTER SEQUENCE athena_openapi.api_path_metadata_id_seq OWNED BY athena_openapi.api_path_metadata.id;


--
-- Name: api_spec; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.api_spec (
    first_time_seen timestamp with time zone,
    id bigint NOT NULL,
    last_sync_time timestamp with time zone,
    project_id bigint NOT NULL,
    version character varying(10) NOT NULL,
    name character varying(100) NOT NULL,
    title character varying(100) NOT NULL
);


ALTER TABLE athena_openapi.api_spec OWNER TO postgres;

--
-- Name: api_spec_id_seq; Type: SEQUENCE; Schema: athena_openapi; Owner: postgres
--

CREATE SEQUENCE athena_openapi.api_spec_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_openapi.api_spec_id_seq OWNER TO postgres;

--
-- Name: api_spec_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_openapi; Owner: postgres
--

ALTER SEQUENCE athena_openapi.api_spec_id_seq OWNED BY athena_openapi.api_spec.id;


--
-- Name: api_spec_metadata; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.api_spec_metadata (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(2000) NOT NULL
);


ALTER TABLE athena_openapi.api_spec_metadata OWNER TO postgres;

--
-- Name: api_spec_metadata_id_seq; Type: SEQUENCE; Schema: athena_openapi; Owner: postgres
--

CREATE SEQUENCE athena_openapi.api_spec_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_openapi.api_spec_metadata_id_seq OWNER TO postgres;

--
-- Name: api_spec_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_openapi; Owner: postgres
--

ALTER SEQUENCE athena_openapi.api_spec_metadata_id_seq OWNED BY athena_openapi.api_spec_metadata.id;


--
-- Name: api_spec_metadata_mid; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.api_spec_metadata_mid (
    metadata_id bigint NOT NULL,
    spec_id bigint NOT NULL
);


ALTER TABLE athena_openapi.api_spec_metadata_mid OWNER TO postgres;

--
-- Name: path_metadata_mid; Type: TABLE; Schema: athena_openapi; Owner: postgres
--

CREATE TABLE athena_openapi.path_metadata_mid (
    metadata_id bigint NOT NULL,
    path_id bigint NOT NULL
);


ALTER TABLE athena_openapi.path_metadata_mid OWNER TO postgres;

--
-- Name: execution; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.execution (
    before_class_end_time timestamp with time zone,
    before_class_start_time timestamp with time zone,
    before_method_end_time timestamp with time zone,
    before_method_start_time timestamp with time zone,
    end_time timestamp with time zone NOT NULL,
    executor_id bigint NOT NULL,
    id bigint NOT NULL,
    pipeline_id bigint NOT NULL,
    start_time timestamp with time zone NOT NULL,
    status_id bigint NOT NULL,
    test_end_time timestamp with time zone,
    test_start_time timestamp with time zone,
    class_name character varying(300) NOT NULL,
    method_name character varying(300) NOT NULL,
    package_name character varying(300) NOT NULL,
    parameters character varying(2000)
);


ALTER TABLE athena_pipeline.execution OWNER TO postgres;

--
-- Name: execution_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.execution_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.execution_id_seq OWNER TO postgres;

--
-- Name: execution_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.execution_id_seq OWNED BY athena_pipeline.execution.id;


--
-- Name: execution_metadata; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.execution_metadata (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(2000) NOT NULL
);


ALTER TABLE athena_pipeline.execution_metadata OWNER TO postgres;

--
-- Name: execution_metadata_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.execution_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.execution_metadata_id_seq OWNER TO postgres;

--
-- Name: execution_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.execution_metadata_id_seq OWNED BY athena_pipeline.execution_metadata.id;


--
-- Name: execution_metadata_mid; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.execution_metadata_mid (
    execution_id bigint NOT NULL,
    metadata_id bigint NOT NULL
);


ALTER TABLE athena_pipeline.execution_metadata_mid OWNER TO postgres;

--
-- Name: pipeline; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.pipeline (
    end_date timestamp with time zone,
    environment_id bigint NOT NULL,
    id bigint NOT NULL,
    start_date timestamp with time zone NOT NULL,
    version_id bigint NOT NULL,
    name character varying(100) NOT NULL,
    number character varying(100) NOT NULL,
    description character varying(300) NOT NULL
);


ALTER TABLE athena_pipeline.pipeline OWNER TO postgres;

--
-- Name: pipeline_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.pipeline_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.pipeline_id_seq OWNER TO postgres;

--
-- Name: pipeline_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.pipeline_id_seq OWNED BY athena_pipeline.pipeline.id;


--
-- Name: pipeline_metadata; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.pipeline_metadata (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(2000) NOT NULL
);


ALTER TABLE athena_pipeline.pipeline_metadata OWNER TO postgres;

--
-- Name: pipeline_metadata_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.pipeline_metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.pipeline_metadata_id_seq OWNER TO postgres;

--
-- Name: pipeline_metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.pipeline_metadata_id_seq OWNED BY athena_pipeline.pipeline_metadata.id;


--
-- Name: pipeline_metadata_mid; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.pipeline_metadata_mid (
    metadata_id bigint NOT NULL,
    pipeline_id bigint NOT NULL
);


ALTER TABLE athena_pipeline.pipeline_metadata_mid OWNER TO postgres;

--
-- Name: scenario_execution; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.scenario_execution (
    before_scenario_end_time timestamp with time zone,
    before_scenario_start_time timestamp with time zone,
    end_time timestamp with time zone NOT NULL,
    executor_id bigint NOT NULL,
    id bigint NOT NULL,
    pipeline_id bigint NOT NULL,
    start_time timestamp with time zone NOT NULL,
    status_id bigint NOT NULL,
    scenario character varying(500) NOT NULL,
    feature character varying(1000) NOT NULL,
    parameters character varying(2000)
);


ALTER TABLE athena_pipeline.scenario_execution OWNER TO postgres;

--
-- Name: scenario_execution_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.scenario_execution_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.scenario_execution_id_seq OWNER TO postgres;

--
-- Name: scenario_execution_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.scenario_execution_id_seq OWNED BY athena_pipeline.scenario_execution.id;


--
-- Name: scenario_metadata_mid; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.scenario_metadata_mid (
    execution_id bigint NOT NULL,
    metadata_id bigint NOT NULL
);


ALTER TABLE athena_pipeline.scenario_metadata_mid OWNER TO postgres;

--
-- Name: status; Type: TABLE; Schema: athena_pipeline; Owner: postgres
--

CREATE TABLE athena_pipeline.status (
    id bigint NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE athena_pipeline.status OWNER TO postgres;

--
-- Name: status_id_seq; Type: SEQUENCE; Schema: athena_pipeline; Owner: postgres
--

CREATE SEQUENCE athena_pipeline.status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_pipeline.status_id_seq OWNER TO postgres;

--
-- Name: status_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_pipeline; Owner: postgres
--

ALTER SEQUENCE athena_pipeline.status_id_seq OWNED BY athena_pipeline.status.id;


--
-- Name: cycle; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.cycle (
    unique_hash integer NOT NULL,
    end_date timestamp with time zone,
    id bigint NOT NULL,
    start_date timestamp with time zone NOT NULL,
    version_id bigint,
    code character varying(10) NOT NULL,
    name character varying(300) NOT NULL
);


ALTER TABLE athena_tms.cycle OWNER TO postgres;

--
-- Name: cycle_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.cycle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.cycle_id_seq OWNER TO postgres;

--
-- Name: cycle_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.cycle_id_seq OWNED BY athena_tms.cycle.id;


--
-- Name: execution; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.execution (
    created timestamp with time zone NOT NULL,
    cycle_id bigint NOT NULL,
    executed timestamp with time zone,
    executor_id bigint,
    id bigint NOT NULL,
    item_id bigint NOT NULL,
    status_id bigint NOT NULL
);


ALTER TABLE athena_tms.execution OWNER TO postgres;

--
-- Name: execution_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.execution_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.execution_id_seq OWNER TO postgres;

--
-- Name: execution_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.execution_id_seq OWNED BY athena_tms.execution.id;


--
-- Name: item; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.item (
    created_by bigint NOT NULL,
    created_on timestamp with time zone NOT NULL,
    id bigint NOT NULL,
    priority_id bigint NOT NULL,
    project_id bigint NOT NULL,
    status_id bigint NOT NULL,
    type_id bigint NOT NULL,
    updated_by bigint,
    updated_on timestamp with time zone,
    code character varying(15) NOT NULL,
    name character varying(300) NOT NULL
);


ALTER TABLE athena_tms.item OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.item_id_seq OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.item_id_seq OWNED BY athena_tms.item.id;


--
-- Name: item_metadata_mid; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.item_metadata_mid (
    item_id bigint NOT NULL,
    metadata_id bigint NOT NULL
);


ALTER TABLE athena_tms.item_metadata_mid OWNER TO postgres;

--
-- Name: item_version_mid; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.item_version_mid (
    item_id bigint NOT NULL,
    version_id bigint NOT NULL
);


ALTER TABLE athena_tms.item_version_mid OWNER TO postgres;

--
-- Name: metadata; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.metadata (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(2000) NOT NULL
);


ALTER TABLE athena_tms.metadata OWNER TO postgres;

--
-- Name: metadata_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.metadata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.metadata_id_seq OWNER TO postgres;

--
-- Name: metadata_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.metadata_id_seq OWNED BY athena_tms.metadata.id;


--
-- Name: priority; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.priority (
    id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_tms.priority OWNER TO postgres;

--
-- Name: priority_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.priority_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.priority_id_seq OWNER TO postgres;

--
-- Name: priority_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.priority_id_seq OWNED BY athena_tms.priority.id;


--
-- Name: status; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.status (
    id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_tms.status OWNER TO postgres;

--
-- Name: status_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.status_id_seq OWNER TO postgres;

--
-- Name: status_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.status_id_seq OWNED BY athena_tms.status.id;


--
-- Name: status_transition; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.status_transition (
    author bigint NOT NULL,
    from_status bigint NOT NULL,
    id bigint NOT NULL,
    item_id bigint NOT NULL,
    occurred timestamp with time zone,
    to_status bigint NOT NULL
);


ALTER TABLE athena_tms.status_transition OWNER TO postgres;

--
-- Name: status_transition_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.status_transition_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.status_transition_id_seq OWNER TO postgres;

--
-- Name: status_transition_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.status_transition_id_seq OWNED BY athena_tms.status_transition.id;


--
-- Name: sync_info; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.sync_info (
    end_time timestamp with time zone NOT NULL,
    id bigint NOT NULL,
    project_id bigint NOT NULL,
    start_time timestamp with time zone NOT NULL,
    action character varying(50) NOT NULL,
    component character varying(100) NOT NULL
);


ALTER TABLE athena_tms.sync_info OWNER TO postgres;

--
-- Name: sync_info_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.sync_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.sync_info_id_seq OWNER TO postgres;

--
-- Name: sync_info_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.sync_info_id_seq OWNED BY athena_tms.sync_info.id;


--
-- Name: type; Type: TABLE; Schema: athena_tms; Owner: postgres
--

CREATE TABLE athena_tms.type (
    id bigint NOT NULL,
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE athena_tms.type OWNER TO postgres;

--
-- Name: type_id_seq; Type: SEQUENCE; Schema: athena_tms; Owner: postgres
--

CREATE SEQUENCE athena_tms.type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE athena_tms.type_id_seq OWNER TO postgres;

--
-- Name: type_id_seq; Type: SEQUENCE OWNED BY; Schema: athena_tms; Owner: postgres
--

ALTER SEQUENCE athena_tms.type_id_seq OWNED BY athena_tms.type.id;


--
-- Name: app_version id; Type: DEFAULT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.app_version ALTER COLUMN id SET DEFAULT nextval('athena_core.app_version_id_seq'::regclass);


--
-- Name: environment id; Type: DEFAULT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.environment ALTER COLUMN id SET DEFAULT nextval('athena_core.environment_id_seq'::regclass);


--
-- Name: project id; Type: DEFAULT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.project ALTER COLUMN id SET DEFAULT nextval('athena_core.project_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core."user" ALTER COLUMN id SET DEFAULT nextval('athena_core.user_id_seq'::regclass);


--
-- Name: user_alias id; Type: DEFAULT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.user_alias ALTER COLUMN id SET DEFAULT nextval('athena_core.user_alias_id_seq'::regclass);


--
-- Name: commit id; Type: DEFAULT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit ALTER COLUMN id SET DEFAULT nextval('athena_git.commit_id_seq'::regclass);


--
-- Name: commit_metadata id; Type: DEFAULT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_metadata ALTER COLUMN id SET DEFAULT nextval('athena_git.commit_metadata_id_seq'::regclass);


--
-- Name: diff_entry id; Type: DEFAULT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.diff_entry ALTER COLUMN id SET DEFAULT nextval('athena_git.diff_entry_id_seq'::regclass);


--
-- Name: repository id; Type: DEFAULT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.repository ALTER COLUMN id SET DEFAULT nextval('athena_git.repository_id_seq'::regclass);


--
-- Name: tag id; Type: DEFAULT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.tag ALTER COLUMN id SET DEFAULT nextval('athena_git.tag_id_seq'::regclass);


--
-- Name: container id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container ALTER COLUMN id SET DEFAULT nextval('athena_kube.container_id_seq'::regclass);


--
-- Name: container_metadata id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata ALTER COLUMN id SET DEFAULT nextval('athena_kube.container_metadata_id_seq'::regclass);


--
-- Name: pod id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_id_seq'::regclass);


--
-- Name: pod_annotation id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_annotation_id_seq'::regclass);


--
-- Name: pod_label id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_label_id_seq'::regclass);


--
-- Name: pod_metadata id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_metadata_id_seq'::regclass);


--
-- Name: pod_selector id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_selector_id_seq'::regclass);


--
-- Name: pod_status id; Type: DEFAULT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_status ALTER COLUMN id SET DEFAULT nextval('athena_kube.pod_status_id_seq'::regclass);


--
-- Name: action id; Type: DEFAULT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.action ALTER COLUMN id SET DEFAULT nextval('athena_metric.action_id_seq'::regclass);


--
-- Name: metric id; Type: DEFAULT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.metric ALTER COLUMN id SET DEFAULT nextval('athena_metric.metric_id_seq'::regclass);


--
-- Name: api_path id; Type: DEFAULT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_path ALTER COLUMN id SET DEFAULT nextval('athena_openapi.api_path_id_seq'::regclass);


--
-- Name: api_path_metadata id; Type: DEFAULT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_path_metadata ALTER COLUMN id SET DEFAULT nextval('athena_openapi.api_path_metadata_id_seq'::regclass);


--
-- Name: api_spec id; Type: DEFAULT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec ALTER COLUMN id SET DEFAULT nextval('athena_openapi.api_spec_id_seq'::regclass);


--
-- Name: api_spec_metadata id; Type: DEFAULT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec_metadata ALTER COLUMN id SET DEFAULT nextval('athena_openapi.api_spec_metadata_id_seq'::regclass);


--
-- Name: execution id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.execution_id_seq'::regclass);


--
-- Name: execution_metadata id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution_metadata ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.execution_metadata_id_seq'::regclass);


--
-- Name: pipeline id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.pipeline_id_seq'::regclass);


--
-- Name: pipeline_metadata id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline_metadata ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.pipeline_metadata_id_seq'::regclass);


--
-- Name: scenario_execution id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_execution ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.scenario_execution_id_seq'::regclass);


--
-- Name: status id; Type: DEFAULT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.status ALTER COLUMN id SET DEFAULT nextval('athena_pipeline.status_id_seq'::regclass);


--
-- Name: cycle id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.cycle ALTER COLUMN id SET DEFAULT nextval('athena_tms.cycle_id_seq'::regclass);


--
-- Name: execution id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution ALTER COLUMN id SET DEFAULT nextval('athena_tms.execution_id_seq'::regclass);


--
-- Name: item id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item ALTER COLUMN id SET DEFAULT nextval('athena_tms.item_id_seq'::regclass);


--
-- Name: metadata id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.metadata ALTER COLUMN id SET DEFAULT nextval('athena_tms.metadata_id_seq'::regclass);


--
-- Name: priority id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.priority ALTER COLUMN id SET DEFAULT nextval('athena_tms.priority_id_seq'::regclass);


--
-- Name: status id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status ALTER COLUMN id SET DEFAULT nextval('athena_tms.status_id_seq'::regclass);


--
-- Name: status_transition id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition ALTER COLUMN id SET DEFAULT nextval('athena_tms.status_transition_id_seq'::regclass);


--
-- Name: sync_info id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.sync_info ALTER COLUMN id SET DEFAULT nextval('athena_tms.sync_info_id_seq'::regclass);


--
-- Name: type id; Type: DEFAULT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.type ALTER COLUMN id SET DEFAULT nextval('athena_tms.type_id_seq'::regclass);


--
-- Data for Name: app_version; Type: TABLE DATA; Schema: athena_core; Owner: postgres
--

COPY athena_core.app_version (id, project_id, code, name) FROM stdin;
1	1	1.01	1.0 version for Administration
2	1	1.11	1.1 version for Administration
3	1	2-151	2-15 version for Administration
4	1	2021-021	2021-02 version for Administration
5	1	202102031	20210203 version for Administration
6	2	v2021-032	v2021-03 version for Storage
7	2	v20212	v2021 version for Storage
8	2	20212	2021 version for Storage
9	2	1232	123 version for Storage
10	2	1.12	1.1 version for Storage
11	3	1.23	1.2 version for Automation
12	3	1.33	1.3 version for Automation
13	3	1.43	1.4 version for Automation
14	3	1.53	1.5 version for Automation
15	3	1.63	1.6 version for Automation
16	4	1.14	1.1 version for Finance
17	4	1.24	1.2 version for Finance
18	4	1.34	1.3 version for Finance
19	4	1.44	1.4 version for Finance
20	4	1.54	1.5 version for Finance
21	5	1.15	1.1 version for Organization
22	5	1.25	1.2 version for Organization
23	5	1.35	1.3 version for Organization
24	5	1.45	1.4 version for Organization
25	5	1.55	1.5 version for Organization
26	6	QKHTPNZ	EKZLOALH
\.


--
-- Data for Name: environment; Type: TABLE DATA; Schema: athena_core; Owner: postgres
--

COPY athena_core.environment (id, project_id, code, name) FROM stdin;
1	1	DEV1	Development environment for Administration
2	1	SMK1	Development Smoke environment for Administration
3	1	QA1	QA environment for Administration
4	1	STG1	Stagin environment for Administration
5	1	PRD1	Production environment for Administration
6	2	DEV2	Development environment for Storage
7	2	SMK2	Development Smoke environment for Storage
8	2	QA2	QA environment for Storage
9	2	STG2	Stagin environment for Storage
10	2	PRD2	Production environment for Storage
11	3	DEV3	Development environment for Automation
12	3	SMK3	Development Smoke environment for Automation
13	3	QA3	QA environment for Automation
14	3	STG3	Stagin environment for Automation
15	3	PRD3	Production environment for Automation
16	4	DEV4	Development environment for Finance
17	4	SMK4	Development Smoke environment for Finance
18	4	QA4	QA environment for Finance
19	4	STG4	Stagin environment for Finance
20	4	PRD4	Production environment for Finance
21	5	DEV5	Development environment for Organization
22	5	SMK5	Development Smoke environment for Organization
23	5	QA5	QA environment for Organization
24	5	STG5	Stagin environment for Organization
25	5	PRD5	Production environment for Organization
26	6	HCUZGGPVQ	WBNXOKXRRE
\.


--
-- Data for Name: project; Type: TABLE DATA; Schema: athena_core; Owner: postgres
--

COPY athena_core.project (id, code, name) FROM stdin;
1	ADM	Administration
2	STG	Storage
3	ATO	Automation
4	FIN	Finance
5	ORG	Organization
6	YDMRVEC	ZLXHZQCQQY
7	MSFJHQTELC	DCQMD
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: athena_core; Owner: postgres
--

COPY athena_core."user" (id, username) FROM stdin;
1	AKeshmiri
2	JDoe
3	AHolmes
4	BRyan
5	WJGQ
\.


--
-- Data for Name: user_alias; Type: TABLE DATA; Schema: athena_core; Owner: postgres
--

COPY athena_core.user_alias (id, user_id, alias) FROM stdin;
1	1	ak
2	2	jd
3	2	jone doe
4	5	XTJGQWTEVY
5	5	UYN
6	5	VPS
7	5	SFN
8	5	HAG
9	5	IGFKFPHCBD
\.


--
-- Data for Name: commit; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.commit (line_deleted, line_inserted, parent_count, total_file, author_id, commit_time, committer_id, id, repository_id, hash, parent_hash, short_message) FROM stdin;
\.


--
-- Data for Name: commit_metadata; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.commit_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: commit_metadata_mid; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.commit_metadata_mid (commit_id, metadata_id) FROM stdin;
\.


--
-- Data for Name: commit_tag_mid; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.commit_tag_mid (commit_id, tag_id) FROM stdin;
\.


--
-- Data for Name: diff_entry; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.diff_entry (deleted, inserted, commit_id, id, change_type, new, old) FROM stdin;
\.


--
-- Data for Name: repository; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.repository (id, last_sync, name, url) FROM stdin;
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: athena_git; Owner: postgres
--

COPY athena_git.tag (id, hash, name) FROM stdin;
\.


--
-- Data for Name: container; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.container (ready, restart_count, started, id, last_sync, pod_id, started_at, type, image_id, name, image) FROM stdin;
\.


--
-- Data for Name: container_metadata; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.container_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: container_metadata_mid; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.container_metadata_mid (container_id, metadata_id) FROM stdin;
\.


--
-- Data for Name: pod; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod (created_at, deleted_at, id, last_sync, project_id, status_id, uid, namespace, hostname, node_name, name) FROM stdin;
\.


--
-- Data for Name: pod_annotation; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_annotation (id, name, value) FROM stdin;
\.


--
-- Data for Name: pod_annotation_mid; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_annotation_mid (annotation_id, pod_id) FROM stdin;
\.


--
-- Data for Name: pod_label; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_label (id, name, value) FROM stdin;
\.


--
-- Data for Name: pod_label_mid; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_label_mid (label_id, pod_id) FROM stdin;
\.


--
-- Data for Name: pod_metadata; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: pod_metadata_mid; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_metadata_mid (metadata_id, pod_id) FROM stdin;
\.


--
-- Data for Name: pod_selector; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_selector (id, name, value) FROM stdin;
\.


--
-- Data for Name: pod_selector_mid; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_selector_mid (pod_id, selector_id) FROM stdin;
\.


--
-- Data for Name: pod_status; Type: TABLE DATA; Schema: athena_kube; Owner: postgres
--

COPY athena_kube.pod_status (id, name, phase, message, reason) FROM stdin;
\.


--
-- Data for Name: action; Type: TABLE DATA; Schema: athena_metric; Owner: postgres
--

COPY athena_metric.action (id, category, name, type, target, command, parameter) FROM stdin;
\.


--
-- Data for Name: metric; Type: TABLE DATA; Schema: athena_metric; Owner: postgres
--

COPY athena_metric.metric (action_id, action_time, duration, environment_id, id, project_id) FROM stdin;
\.


--
-- Data for Name: api_path; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.api_path (first_time_seen, id, last_sync_time, spec_id, method, url, title, description, parameters) FROM stdin;
\.


--
-- Data for Name: api_path_metadata; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.api_path_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: api_spec; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.api_spec (first_time_seen, id, last_sync_time, project_id, version, name, title) FROM stdin;
\.


--
-- Data for Name: api_spec_metadata; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.api_spec_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: api_spec_metadata_mid; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.api_spec_metadata_mid (metadata_id, spec_id) FROM stdin;
\.


--
-- Data for Name: path_metadata_mid; Type: TABLE DATA; Schema: athena_openapi; Owner: postgres
--

COPY athena_openapi.path_metadata_mid (metadata_id, path_id) FROM stdin;
\.


--
-- Data for Name: execution; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.execution (before_class_end_time, before_class_start_time, before_method_end_time, before_method_start_time, end_time, executor_id, id, pipeline_id, start_time, status_id, test_end_time, test_start_time, class_name, method_name, package_name, parameters) FROM stdin;
\.


--
-- Data for Name: execution_metadata; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.execution_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: execution_metadata_mid; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.execution_metadata_mid (execution_id, metadata_id) FROM stdin;
\.


--
-- Data for Name: pipeline; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.pipeline (end_date, environment_id, id, start_date, version_id, name, number, description) FROM stdin;
\.


--
-- Data for Name: pipeline_metadata; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.pipeline_metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: pipeline_metadata_mid; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.pipeline_metadata_mid (metadata_id, pipeline_id) FROM stdin;
\.


--
-- Data for Name: scenario_execution; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.scenario_execution (before_scenario_end_time, before_scenario_start_time, end_time, executor_id, id, pipeline_id, start_time, status_id, scenario, feature, parameters) FROM stdin;
\.


--
-- Data for Name: scenario_metadata_mid; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.scenario_metadata_mid (execution_id, metadata_id) FROM stdin;
\.


--
-- Data for Name: status; Type: TABLE DATA; Schema: athena_pipeline; Owner: postgres
--

COPY athena_pipeline.status (id, name) FROM stdin;
\.


--
-- Data for Name: cycle; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.cycle (unique_hash, end_date, id, start_date, version_id, code, name) FROM stdin;
\.


--
-- Data for Name: execution; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.execution (created, cycle_id, executed, executor_id, id, item_id, status_id) FROM stdin;
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.item (created_by, created_on, id, priority_id, project_id, status_id, type_id, updated_by, updated_on, code, name) FROM stdin;
\.


--
-- Data for Name: item_metadata_mid; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.item_metadata_mid (item_id, metadata_id) FROM stdin;
\.


--
-- Data for Name: item_version_mid; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.item_version_mid (item_id, version_id) FROM stdin;
\.


--
-- Data for Name: metadata; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.metadata (id, name, value) FROM stdin;
\.


--
-- Data for Name: priority; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.priority (id, code, name) FROM stdin;
\.


--
-- Data for Name: status; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.status (id, code, name) FROM stdin;
\.


--
-- Data for Name: status_transition; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.status_transition (author, from_status, id, item_id, occurred, to_status) FROM stdin;
\.


--
-- Data for Name: sync_info; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.sync_info (end_time, id, project_id, start_time, action, component) FROM stdin;
\.


--
-- Data for Name: type; Type: TABLE DATA; Schema: athena_tms; Owner: postgres
--

COPY athena_tms.type (id, code, name) FROM stdin;
\.


--
-- Name: app_version_id_seq; Type: SEQUENCE SET; Schema: athena_core; Owner: postgres
--

SELECT pg_catalog.setval('athena_core.app_version_id_seq', 33, true);


--
-- Name: environment_id_seq; Type: SEQUENCE SET; Schema: athena_core; Owner: postgres
--

SELECT pg_catalog.setval('athena_core.environment_id_seq', 33, true);


--
-- Name: project_id_seq; Type: SEQUENCE SET; Schema: athena_core; Owner: postgres
--

SELECT pg_catalog.setval('athena_core.project_id_seq', 33, true);


--
-- Name: user_alias_id_seq; Type: SEQUENCE SET; Schema: athena_core; Owner: postgres
--

SELECT pg_catalog.setval('athena_core.user_alias_id_seq', 33, true);


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: athena_core; Owner: postgres
--

SELECT pg_catalog.setval('athena_core.user_id_seq', 33, true);


--
-- Name: commit_id_seq; Type: SEQUENCE SET; Schema: athena_git; Owner: postgres
--

SELECT pg_catalog.setval('athena_git.commit_id_seq', 1, false);


--
-- Name: commit_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_git; Owner: postgres
--

SELECT pg_catalog.setval('athena_git.commit_metadata_id_seq', 1, false);


--
-- Name: diff_entry_id_seq; Type: SEQUENCE SET; Schema: athena_git; Owner: postgres
--

SELECT pg_catalog.setval('athena_git.diff_entry_id_seq', 1, false);


--
-- Name: repository_id_seq; Type: SEQUENCE SET; Schema: athena_git; Owner: postgres
--

SELECT pg_catalog.setval('athena_git.repository_id_seq', 1, false);


--
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: athena_git; Owner: postgres
--

SELECT pg_catalog.setval('athena_git.tag_id_seq', 1, false);


--
-- Name: container_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.container_id_seq', 1, false);


--
-- Name: container_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.container_metadata_id_seq', 1, false);


--
-- Name: pod_annotation_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_annotation_id_seq', 1, false);


--
-- Name: pod_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_id_seq', 1, false);


--
-- Name: pod_label_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_label_id_seq', 1, false);


--
-- Name: pod_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_metadata_id_seq', 1, false);


--
-- Name: pod_selector_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_selector_id_seq', 1, false);


--
-- Name: pod_status_id_seq; Type: SEQUENCE SET; Schema: athena_kube; Owner: postgres
--

SELECT pg_catalog.setval('athena_kube.pod_status_id_seq', 1, false);


--
-- Name: action_id_seq; Type: SEQUENCE SET; Schema: athena_metric; Owner: postgres
--

SELECT pg_catalog.setval('athena_metric.action_id_seq', 1, false);


--
-- Name: metric_id_seq; Type: SEQUENCE SET; Schema: athena_metric; Owner: postgres
--

SELECT pg_catalog.setval('athena_metric.metric_id_seq', 1, false);


--
-- Name: api_path_id_seq; Type: SEQUENCE SET; Schema: athena_openapi; Owner: postgres
--

SELECT pg_catalog.setval('athena_openapi.api_path_id_seq', 1, false);


--
-- Name: api_path_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_openapi; Owner: postgres
--

SELECT pg_catalog.setval('athena_openapi.api_path_metadata_id_seq', 1, false);


--
-- Name: api_spec_id_seq; Type: SEQUENCE SET; Schema: athena_openapi; Owner: postgres
--

SELECT pg_catalog.setval('athena_openapi.api_spec_id_seq', 1, false);


--
-- Name: api_spec_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_openapi; Owner: postgres
--

SELECT pg_catalog.setval('athena_openapi.api_spec_metadata_id_seq', 1, false);


--
-- Name: execution_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.execution_id_seq', 1, false);


--
-- Name: execution_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.execution_metadata_id_seq', 1, false);


--
-- Name: pipeline_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.pipeline_id_seq', 1, false);


--
-- Name: pipeline_metadata_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.pipeline_metadata_id_seq', 1, false);


--
-- Name: scenario_execution_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.scenario_execution_id_seq', 1, false);


--
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: athena_pipeline; Owner: postgres
--

SELECT pg_catalog.setval('athena_pipeline.status_id_seq', 1, false);


--
-- Name: cycle_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.cycle_id_seq', 1, false);


--
-- Name: execution_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.execution_id_seq', 1, false);


--
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.item_id_seq', 1, false);


--
-- Name: metadata_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.metadata_id_seq', 1, false);


--
-- Name: priority_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.priority_id_seq', 1, false);


--
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.status_id_seq', 1, false);


--
-- Name: status_transition_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.status_transition_id_seq', 1, false);


--
-- Name: sync_info_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.sync_info_id_seq', 1, false);


--
-- Name: type_id_seq; Type: SEQUENCE SET; Schema: athena_tms; Owner: postgres
--

SELECT pg_catalog.setval('athena_tms.type_id_seq', 1, false);


--
-- Name: app_version app_version_code_key; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.app_version
    ADD CONSTRAINT app_version_code_key UNIQUE (code);


--
-- Name: app_version app_version_pkey; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.app_version
    ADD CONSTRAINT app_version_pkey PRIMARY KEY (id);


--
-- Name: environment environment_code_key; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.environment
    ADD CONSTRAINT environment_code_key UNIQUE (code);


--
-- Name: environment environment_pkey; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.environment
    ADD CONSTRAINT environment_pkey PRIMARY KEY (id);


--
-- Name: project project_code_key; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.project
    ADD CONSTRAINT project_code_key UNIQUE (code);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: user_alias user_alias_alias_key; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.user_alias
    ADD CONSTRAINT user_alias_alias_key UNIQUE (alias);


--
-- Name: user_alias user_alias_pkey; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.user_alias
    ADD CONSTRAINT user_alias_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: user user_username_key; Type: CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- Name: commit commit_hash_key; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit
    ADD CONSTRAINT commit_hash_key UNIQUE (hash);


--
-- Name: commit_metadata_mid commit_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_metadata_mid
    ADD CONSTRAINT commit_metadata_mid_pkey PRIMARY KEY (commit_id, metadata_id);


--
-- Name: commit_metadata commit_metadata_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_metadata
    ADD CONSTRAINT commit_metadata_pkey PRIMARY KEY (id);


--
-- Name: commit commit_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit
    ADD CONSTRAINT commit_pkey PRIMARY KEY (id);


--
-- Name: commit_tag_mid commit_tag_mid_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_tag_mid
    ADD CONSTRAINT commit_tag_mid_pkey PRIMARY KEY (commit_id, tag_id);


--
-- Name: diff_entry diff_entry_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.diff_entry
    ADD CONSTRAINT diff_entry_pkey PRIMARY KEY (id);


--
-- Name: repository repository_name_key; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.repository
    ADD CONSTRAINT repository_name_key UNIQUE (name);


--
-- Name: repository repository_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.repository
    ADD CONSTRAINT repository_pkey PRIMARY KEY (id);


--
-- Name: repository repository_url_key; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.repository
    ADD CONSTRAINT repository_url_key UNIQUE (url);


--
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- Name: container_metadata_mid container_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata_mid
    ADD CONSTRAINT container_metadata_mid_pkey PRIMARY KEY (container_id, metadata_id);


--
-- Name: container_metadata container_metadata_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata
    ADD CONSTRAINT container_metadata_pkey PRIMARY KEY (id);


--
-- Name: container container_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container
    ADD CONSTRAINT container_pkey PRIMARY KEY (id);


--
-- Name: pod_annotation_mid pod_annotation_mid_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation_mid
    ADD CONSTRAINT pod_annotation_mid_pkey PRIMARY KEY (annotation_id, pod_id);


--
-- Name: pod_annotation pod_annotation_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation
    ADD CONSTRAINT pod_annotation_pkey PRIMARY KEY (id);


--
-- Name: pod_label_mid pod_label_mid_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label_mid
    ADD CONSTRAINT pod_label_mid_pkey PRIMARY KEY (label_id, pod_id);


--
-- Name: pod_label pod_label_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label
    ADD CONSTRAINT pod_label_pkey PRIMARY KEY (id);


--
-- Name: pod_metadata_mid pod_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata_mid
    ADD CONSTRAINT pod_metadata_mid_pkey PRIMARY KEY (metadata_id, pod_id);


--
-- Name: pod_metadata pod_metadata_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata
    ADD CONSTRAINT pod_metadata_pkey PRIMARY KEY (id);


--
-- Name: pod pod_name_key; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod
    ADD CONSTRAINT pod_name_key UNIQUE (name);


--
-- Name: pod pod_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod
    ADD CONSTRAINT pod_pkey PRIMARY KEY (id);


--
-- Name: pod_selector_mid pod_selector_mid_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector_mid
    ADD CONSTRAINT pod_selector_mid_pkey PRIMARY KEY (pod_id, selector_id);


--
-- Name: pod_selector pod_selector_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector
    ADD CONSTRAINT pod_selector_pkey PRIMARY KEY (id);


--
-- Name: pod_status pod_status_pkey; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_status
    ADD CONSTRAINT pod_status_pkey PRIMARY KEY (id);


--
-- Name: pod pod_uid_key; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod
    ADD CONSTRAINT pod_uid_key UNIQUE (uid);


--
-- Name: pod_annotation uniquepodannotationnamevalue; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation
    ADD CONSTRAINT uniquepodannotationnamevalue UNIQUE (name, value);


--
-- Name: container_metadata uniquepodcontainermetadatanamevalue; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata
    ADD CONSTRAINT uniquepodcontainermetadatanamevalue UNIQUE (name, value);


--
-- Name: pod_label uniquepodlabelnamevalue; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label
    ADD CONSTRAINT uniquepodlabelnamevalue UNIQUE (name, value);


--
-- Name: pod_metadata uniquepodmetadatanamevalue; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata
    ADD CONSTRAINT uniquepodmetadatanamevalue UNIQUE (name, value);


--
-- Name: pod_selector uniquepodselectornamevalue; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector
    ADD CONSTRAINT uniquepodselectornamevalue UNIQUE (name, value);


--
-- Name: pod_status uniquepodstatus; Type: CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_status
    ADD CONSTRAINT uniquepodstatus UNIQUE (name, phase, message, reason);


--
-- Name: action action_pkey; Type: CONSTRAINT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.action
    ADD CONSTRAINT action_pkey PRIMARY KEY (id);


--
-- Name: metric metric_pkey; Type: CONSTRAINT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.metric
    ADD CONSTRAINT metric_pkey PRIMARY KEY (id);


--
-- Name: api_path_metadata api_path_metadata_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_path_metadata
    ADD CONSTRAINT api_path_metadata_pkey PRIMARY KEY (id);


--
-- Name: api_path api_path_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_path
    ADD CONSTRAINT api_path_pkey PRIMARY KEY (id);


--
-- Name: api_spec_metadata_mid api_spec_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec_metadata_mid
    ADD CONSTRAINT api_spec_metadata_mid_pkey PRIMARY KEY (metadata_id, spec_id);


--
-- Name: api_spec_metadata api_spec_metadata_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec_metadata
    ADD CONSTRAINT api_spec_metadata_pkey PRIMARY KEY (id);


--
-- Name: api_spec api_spec_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec
    ADD CONSTRAINT api_spec_pkey PRIMARY KEY (id);


--
-- Name: path_metadata_mid path_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.path_metadata_mid
    ADD CONSTRAINT path_metadata_mid_pkey PRIMARY KEY (metadata_id, path_id);


--
-- Name: execution_metadata_mid execution_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution_metadata_mid
    ADD CONSTRAINT execution_metadata_mid_pkey PRIMARY KEY (execution_id, metadata_id);


--
-- Name: execution_metadata execution_metadata_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution_metadata
    ADD CONSTRAINT execution_metadata_pkey PRIMARY KEY (id);


--
-- Name: execution execution_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution
    ADD CONSTRAINT execution_pkey PRIMARY KEY (id);


--
-- Name: pipeline_metadata_mid pipeline_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline_metadata_mid
    ADD CONSTRAINT pipeline_metadata_mid_pkey PRIMARY KEY (metadata_id, pipeline_id);


--
-- Name: pipeline_metadata pipeline_metadata_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline_metadata
    ADD CONSTRAINT pipeline_metadata_pkey PRIMARY KEY (id);


--
-- Name: pipeline pipeline_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline
    ADD CONSTRAINT pipeline_pkey PRIMARY KEY (id);


--
-- Name: scenario_execution scenario_execution_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_execution
    ADD CONSTRAINT scenario_execution_pkey PRIMARY KEY (id);


--
-- Name: scenario_metadata_mid scenario_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_metadata_mid
    ADD CONSTRAINT scenario_metadata_mid_pkey PRIMARY KEY (execution_id, metadata_id);


--
-- Name: status status_name_key; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.status
    ADD CONSTRAINT status_name_key UNIQUE (name);


--
-- Name: status status_pkey; Type: CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: cycle cycle_code_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.cycle
    ADD CONSTRAINT cycle_code_key UNIQUE (code);


--
-- Name: cycle cycle_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.cycle
    ADD CONSTRAINT cycle_pkey PRIMARY KEY (id);


--
-- Name: cycle cycle_unique_hash_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.cycle
    ADD CONSTRAINT cycle_unique_hash_key UNIQUE (unique_hash);


--
-- Name: execution execution_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution
    ADD CONSTRAINT execution_pkey PRIMARY KEY (id);


--
-- Name: item item_code_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT item_code_key UNIQUE (code);


--
-- Name: item_metadata_mid item_metadata_mid_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_metadata_mid
    ADD CONSTRAINT item_metadata_mid_pkey PRIMARY KEY (item_id, metadata_id);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: item_version_mid item_version_mid_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_version_mid
    ADD CONSTRAINT item_version_mid_pkey PRIMARY KEY (item_id, version_id);


--
-- Name: metadata metadata_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.metadata
    ADD CONSTRAINT metadata_pkey PRIMARY KEY (id);


--
-- Name: priority priority_code_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.priority
    ADD CONSTRAINT priority_code_key UNIQUE (code);


--
-- Name: priority priority_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.priority
    ADD CONSTRAINT priority_pkey PRIMARY KEY (id);


--
-- Name: status status_code_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status
    ADD CONSTRAINT status_code_key UNIQUE (code);


--
-- Name: status status_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: status_transition status_transition_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition
    ADD CONSTRAINT status_transition_pkey PRIMARY KEY (id);


--
-- Name: sync_info sync_info_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.sync_info
    ADD CONSTRAINT sync_info_pkey PRIMARY KEY (id);


--
-- Name: type type_code_key; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.type
    ADD CONSTRAINT type_code_key UNIQUE (code);


--
-- Name: type type_pkey; Type: CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.type
    ADD CONSTRAINT type_pkey PRIMARY KEY (id);


--
-- Name: idxc1yi6kkb16pl3vg047wm1o204; Type: INDEX; Schema: athena_core; Owner: postgres
--

CREATE INDEX idxc1yi6kkb16pl3vg047wm1o204 ON athena_core.environment USING btree (code);


--
-- Name: idxeg9xtij8mvb1jio1602somk3j; Type: INDEX; Schema: athena_core; Owner: postgres
--

CREATE INDEX idxeg9xtij8mvb1jio1602somk3j ON athena_core.app_version USING btree (code);


--
-- Name: idxeh3nusutt0qy84a4yr9pfxkyg; Type: INDEX; Schema: athena_core; Owner: postgres
--

CREATE INDEX idxeh3nusutt0qy84a4yr9pfxkyg ON athena_core.project USING btree (code);


--
-- Name: idxadh6fulxe89os666w4l6aeepb; Type: INDEX; Schema: athena_metric; Owner: postgres
--

CREATE INDEX idxadh6fulxe89os666w4l6aeepb ON athena_metric.action USING btree (name, type, target, command);


--
-- Name: idx639erqxmw75u6kt3lfksougmb; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idx639erqxmw75u6kt3lfksougmb ON athena_tms.priority USING btree (code);


--
-- Name: idx6cgogdarkq48dlg1lbnv4q1oq; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idx6cgogdarkq48dlg1lbnv4q1oq ON athena_tms.item USING btree (code);


--
-- Name: idx6phb53alqcmb7j69am501p02w; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idx6phb53alqcmb7j69am501p02w ON athena_tms.type USING btree (code);


--
-- Name: idx90n0sv25slo1kmu0tcakhjjed; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idx90n0sv25slo1kmu0tcakhjjed ON athena_tms.status USING btree (code);


--
-- Name: idxk43tb8sntc046yxf4suq076os; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idxk43tb8sntc046yxf4suq076os ON athena_tms.cycle USING btree (code);


--
-- Name: idxynam1aocxtr559vsbvmxpaaa; Type: INDEX; Schema: athena_tms; Owner: postgres
--

CREATE INDEX idxynam1aocxtr559vsbvmxpaaa ON athena_tms.execution USING btree (created, cycle_id, item_id);


--
-- Name: app_version fk5s3l6egklax4c0brnv7yp9cpt; Type: FK CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.app_version
    ADD CONSTRAINT fk5s3l6egklax4c0brnv7yp9cpt FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: environment fke5x1obcek8qq6nlo6p9gn6v0i; Type: FK CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.environment
    ADD CONSTRAINT fke5x1obcek8qq6nlo6p9gn6v0i FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: user_alias fkhx1ayjnubge656u10ug92nkvy; Type: FK CONSTRAINT; Schema: athena_core; Owner: postgres
--

ALTER TABLE ONLY athena_core.user_alias
    ADD CONSTRAINT fkhx1ayjnubge656u10ug92nkvy FOREIGN KEY (user_id) REFERENCES athena_core."user"(id);


--
-- Name: commit_metadata_mid fk33sf8jmbyi046k667sfbeyje0; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_metadata_mid
    ADD CONSTRAINT fk33sf8jmbyi046k667sfbeyje0 FOREIGN KEY (commit_id) REFERENCES athena_git.commit(id);


--
-- Name: diff_entry fk3xg0l50m09tshdlxhluc74811; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.diff_entry
    ADD CONSTRAINT fk3xg0l50m09tshdlxhluc74811 FOREIGN KEY (commit_id) REFERENCES athena_git.commit(id);


--
-- Name: commit fk4c4goeh7k2c5kyso5kteinmrd; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit
    ADD CONSTRAINT fk4c4goeh7k2c5kyso5kteinmrd FOREIGN KEY (author_id) REFERENCES athena_core."user"(id);


--
-- Name: commit fkgqmkfk1wovdbkmbanfrrxc9pp; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit
    ADD CONSTRAINT fkgqmkfk1wovdbkmbanfrrxc9pp FOREIGN KEY (repository_id) REFERENCES athena_git.repository(id);


--
-- Name: commit_tag_mid fkmgcmygnf4hdijqi9eulnuo68y; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_tag_mid
    ADD CONSTRAINT fkmgcmygnf4hdijqi9eulnuo68y FOREIGN KEY (commit_id) REFERENCES athena_git.commit(id);


--
-- Name: commit fkn7wosmmq4lrqcmm1j3qo8v25n; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit
    ADD CONSTRAINT fkn7wosmmq4lrqcmm1j3qo8v25n FOREIGN KEY (committer_id) REFERENCES athena_core."user"(id);


--
-- Name: commit_metadata_mid fkqixdsmsc3kmtb76wmrlso7bak; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_metadata_mid
    ADD CONSTRAINT fkqixdsmsc3kmtb76wmrlso7bak FOREIGN KEY (metadata_id) REFERENCES athena_git.commit_metadata(id);


--
-- Name: commit_tag_mid fkr3nql3m52idajoutdb4y4bv8r; Type: FK CONSTRAINT; Schema: athena_git; Owner: postgres
--

ALTER TABLE ONLY athena_git.commit_tag_mid
    ADD CONSTRAINT fkr3nql3m52idajoutdb4y4bv8r FOREIGN KEY (tag_id) REFERENCES athena_git.tag(id);


--
-- Name: container_metadata_mid fk10djf8c16osesuevhauhvdgim; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata_mid
    ADD CONSTRAINT fk10djf8c16osesuevhauhvdgim FOREIGN KEY (metadata_id) REFERENCES athena_kube.container_metadata(id);


--
-- Name: pod_annotation_mid fk13s5ctig92ec26h8n6ba1ixxe; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation_mid
    ADD CONSTRAINT fk13s5ctig92ec26h8n6ba1ixxe FOREIGN KEY (annotation_id) REFERENCES athena_kube.pod_annotation(id);


--
-- Name: pod_metadata_mid fk8cy7fosd0on1gbkv0pabjabn1; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata_mid
    ADD CONSTRAINT fk8cy7fosd0on1gbkv0pabjabn1 FOREIGN KEY (metadata_id) REFERENCES athena_kube.pod_metadata(id);


--
-- Name: pod_metadata_mid fkbli9a215pefyl6ggyjsvpitid; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_metadata_mid
    ADD CONSTRAINT fkbli9a215pefyl6ggyjsvpitid FOREIGN KEY (pod_id) REFERENCES athena_kube.pod(id);


--
-- Name: pod_annotation_mid fkbsdp5kgjsgktynla364170iok; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_annotation_mid
    ADD CONSTRAINT fkbsdp5kgjsgktynla364170iok FOREIGN KEY (pod_id) REFERENCES athena_kube.pod(id);


--
-- Name: container_metadata_mid fkhv0j50ewf7u0uryjryeauc4cn; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container_metadata_mid
    ADD CONSTRAINT fkhv0j50ewf7u0uryjryeauc4cn FOREIGN KEY (container_id) REFERENCES athena_kube.container(id);


--
-- Name: container fkidh5jk8n2x46l91nky124f39s; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.container
    ADD CONSTRAINT fkidh5jk8n2x46l91nky124f39s FOREIGN KEY (pod_id) REFERENCES athena_kube.pod(id);


--
-- Name: pod_selector_mid fkjdcsuuueuitqspc96yi3ms00p; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector_mid
    ADD CONSTRAINT fkjdcsuuueuitqspc96yi3ms00p FOREIGN KEY (pod_id) REFERENCES athena_kube.pod(id);


--
-- Name: pod_selector_mid fko5hm5rb7fmfxo7qfvw5kaqqbf; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_selector_mid
    ADD CONSTRAINT fko5hm5rb7fmfxo7qfvw5kaqqbf FOREIGN KEY (selector_id) REFERENCES athena_kube.pod_selector(id);


--
-- Name: pod fkpqmjd8srmogc90vxm50sdsed9; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod
    ADD CONSTRAINT fkpqmjd8srmogc90vxm50sdsed9 FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: pod_label_mid fkpwbwcs37hrmr2a7n6its4ber0; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label_mid
    ADD CONSTRAINT fkpwbwcs37hrmr2a7n6its4ber0 FOREIGN KEY (label_id) REFERENCES athena_kube.pod_label(id);


--
-- Name: pod_label_mid fkq3cf8vm17h8stqg7j6chwfw25; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod_label_mid
    ADD CONSTRAINT fkq3cf8vm17h8stqg7j6chwfw25 FOREIGN KEY (pod_id) REFERENCES athena_kube.pod(id);


--
-- Name: pod fkr3p5honftoe7g7fpgabvthmke; Type: FK CONSTRAINT; Schema: athena_kube; Owner: postgres
--

ALTER TABLE ONLY athena_kube.pod
    ADD CONSTRAINT fkr3p5honftoe7g7fpgabvthmke FOREIGN KEY (status_id) REFERENCES athena_kube.pod_status(id);


--
-- Name: metric fk8a8ran7knaybt9c14hepi65ac; Type: FK CONSTRAINT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.metric
    ADD CONSTRAINT fk8a8ran7knaybt9c14hepi65ac FOREIGN KEY (action_id) REFERENCES athena_metric.action(id);


--
-- Name: metric fkciwet60iy67c7vhy09el3ujn0; Type: FK CONSTRAINT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.metric
    ADD CONSTRAINT fkciwet60iy67c7vhy09el3ujn0 FOREIGN KEY (environment_id) REFERENCES athena_core.environment(id);


--
-- Name: metric fkj5lmuglw2ayciv12xr5ocas92; Type: FK CONSTRAINT; Schema: athena_metric; Owner: postgres
--

ALTER TABLE ONLY athena_metric.metric
    ADD CONSTRAINT fkj5lmuglw2ayciv12xr5ocas92 FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: path_metadata_mid fk6xdyw758at5oiivp5eemduw9h; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.path_metadata_mid
    ADD CONSTRAINT fk6xdyw758at5oiivp5eemduw9h FOREIGN KEY (path_id) REFERENCES athena_openapi.api_path(id);


--
-- Name: api_spec_metadata_mid fk9s99xhb1cqcyh67q0w024fgvk; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec_metadata_mid
    ADD CONSTRAINT fk9s99xhb1cqcyh67q0w024fgvk FOREIGN KEY (spec_id) REFERENCES athena_openapi.api_spec(id);


--
-- Name: api_spec_metadata_mid fka7fg5ffcjf1wiao3wffe67c01; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec_metadata_mid
    ADD CONSTRAINT fka7fg5ffcjf1wiao3wffe67c01 FOREIGN KEY (metadata_id) REFERENCES athena_openapi.api_spec_metadata(id);


--
-- Name: api_spec fkcqjl4vog4e9p2efovv4syy5dw; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_spec
    ADD CONSTRAINT fkcqjl4vog4e9p2efovv4syy5dw FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: path_metadata_mid fkg09nlhpnkuyb1bl6uk4vabr57; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.path_metadata_mid
    ADD CONSTRAINT fkg09nlhpnkuyb1bl6uk4vabr57 FOREIGN KEY (metadata_id) REFERENCES athena_openapi.api_path_metadata(id);


--
-- Name: api_path fkoud6vm5jg0hfqqp71xsbw5i4i; Type: FK CONSTRAINT; Schema: athena_openapi; Owner: postgres
--

ALTER TABLE ONLY athena_openapi.api_path
    ADD CONSTRAINT fkoud6vm5jg0hfqqp71xsbw5i4i FOREIGN KEY (spec_id) REFERENCES athena_openapi.api_spec(id);


--
-- Name: execution fk2a1eitje846p27egcbpnpu216; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution
    ADD CONSTRAINT fk2a1eitje846p27egcbpnpu216 FOREIGN KEY (pipeline_id) REFERENCES athena_pipeline.pipeline(id);


--
-- Name: pipeline fk50lhemdi8a649a2wukj2ev0g5; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline
    ADD CONSTRAINT fk50lhemdi8a649a2wukj2ev0g5 FOREIGN KEY (environment_id) REFERENCES athena_core.environment(id);


--
-- Name: scenario_execution fk5vc883jpgoqbpeqk0pxyvthor; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_execution
    ADD CONSTRAINT fk5vc883jpgoqbpeqk0pxyvthor FOREIGN KEY (pipeline_id) REFERENCES athena_pipeline.pipeline(id);


--
-- Name: scenario_metadata_mid fkacptnescmpf3du47da8ihq9ps; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_metadata_mid
    ADD CONSTRAINT fkacptnescmpf3du47da8ihq9ps FOREIGN KEY (execution_id) REFERENCES athena_pipeline.scenario_execution(id);


--
-- Name: pipeline_metadata_mid fkcs5r08ptgvp6eijpulce3dsm0; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline_metadata_mid
    ADD CONSTRAINT fkcs5r08ptgvp6eijpulce3dsm0 FOREIGN KEY (metadata_id) REFERENCES athena_pipeline.pipeline_metadata(id);


--
-- Name: scenario_execution fkdwx5wr1r46p3dd78c9pbp8pbx; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_execution
    ADD CONSTRAINT fkdwx5wr1r46p3dd78c9pbp8pbx FOREIGN KEY (status_id) REFERENCES athena_pipeline.status(id);


--
-- Name: pipeline_metadata_mid fkg07aj55k425j3nwva53a6a2pd; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline_metadata_mid
    ADD CONSTRAINT fkg07aj55k425j3nwva53a6a2pd FOREIGN KEY (pipeline_id) REFERENCES athena_pipeline.pipeline(id);


--
-- Name: scenario_execution fkgytyony4rytg2x9sac7bppbij; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_execution
    ADD CONSTRAINT fkgytyony4rytg2x9sac7bppbij FOREIGN KEY (executor_id) REFERENCES athena_core."user"(id);


--
-- Name: execution_metadata_mid fkickcqlum2vg9let6scpuwkr3b; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution_metadata_mid
    ADD CONSTRAINT fkickcqlum2vg9let6scpuwkr3b FOREIGN KEY (metadata_id) REFERENCES athena_pipeline.execution_metadata(id);


--
-- Name: execution fkl5b63e2atpci888j3vrih9fgk; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution
    ADD CONSTRAINT fkl5b63e2atpci888j3vrih9fgk FOREIGN KEY (status_id) REFERENCES athena_pipeline.status(id);


--
-- Name: execution_metadata_mid fkm9u1oqvmevg9d5qy25mi8h10x; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution_metadata_mid
    ADD CONSTRAINT fkm9u1oqvmevg9d5qy25mi8h10x FOREIGN KEY (execution_id) REFERENCES athena_pipeline.execution(id);


--
-- Name: scenario_metadata_mid fkohg9ibf8as6d3gnd3yqg0hu0f; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.scenario_metadata_mid
    ADD CONSTRAINT fkohg9ibf8as6d3gnd3yqg0hu0f FOREIGN KEY (metadata_id) REFERENCES athena_pipeline.execution_metadata(id);


--
-- Name: pipeline fkpqnqm8tes84nhowr2ryitq258; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.pipeline
    ADD CONSTRAINT fkpqnqm8tes84nhowr2ryitq258 FOREIGN KEY (version_id) REFERENCES athena_core.app_version(id);


--
-- Name: execution fksxj934bma70rv4nxhl9jks1tg; Type: FK CONSTRAINT; Schema: athena_pipeline; Owner: postgres
--

ALTER TABLE ONLY athena_pipeline.execution
    ADD CONSTRAINT fksxj934bma70rv4nxhl9jks1tg FOREIGN KEY (executor_id) REFERENCES athena_core."user"(id);


--
-- Name: status_transition fk3ffxqq0j5njpl8ulantbb3p9; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition
    ADD CONSTRAINT fk3ffxqq0j5njpl8ulantbb3p9 FOREIGN KEY (item_id) REFERENCES athena_tms.item(id);


--
-- Name: item fk95i2undeuwt7lb0uy1md9wkp9; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fk95i2undeuwt7lb0uy1md9wkp9 FOREIGN KEY (type_id) REFERENCES athena_tms.type(id);


--
-- Name: status_transition fk9tb7aqy88ijc3g9rfcxs5nkdf; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition
    ADD CONSTRAINT fk9tb7aqy88ijc3g9rfcxs5nkdf FOREIGN KEY (to_status) REFERENCES athena_tms.status(id);


--
-- Name: item fka5r9sug9g4f4kif98va9ro3l2; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fka5r9sug9g4f4kif98va9ro3l2 FOREIGN KEY (status_id) REFERENCES athena_tms.status(id);


--
-- Name: cycle fkcd7m2868v4ponviyv5olrew1s; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.cycle
    ADD CONSTRAINT fkcd7m2868v4ponviyv5olrew1s FOREIGN KEY (version_id) REFERENCES athena_core.app_version(id);


--
-- Name: item_metadata_mid fkenckba4dsvmvpdnc26iqrntil; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_metadata_mid
    ADD CONSTRAINT fkenckba4dsvmvpdnc26iqrntil FOREIGN KEY (item_id) REFERENCES athena_tms.item(id);


--
-- Name: item fkeybj7ikncx4nkfgrb5i1uadq; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fkeybj7ikncx4nkfgrb5i1uadq FOREIGN KEY (created_by) REFERENCES athena_core."user"(id);


--
-- Name: execution fkf3j4r2wk0rnpm1yw1tfhgh554; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution
    ADD CONSTRAINT fkf3j4r2wk0rnpm1yw1tfhgh554 FOREIGN KEY (item_id) REFERENCES athena_tms.item(id);


--
-- Name: item fkf60hnjyqgladtp0jw5o0n4e9u; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fkf60hnjyqgladtp0jw5o0n4e9u FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: item_version_mid fkkqgy2yskehpg6yftnf6t3gssd; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_version_mid
    ADD CONSTRAINT fkkqgy2yskehpg6yftnf6t3gssd FOREIGN KEY (item_id) REFERENCES athena_tms.item(id);


--
-- Name: item_metadata_mid fkkxa4n4m61qm69fwj6f0udupkn; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_metadata_mid
    ADD CONSTRAINT fkkxa4n4m61qm69fwj6f0udupkn FOREIGN KEY (metadata_id) REFERENCES athena_tms.metadata(id);


--
-- Name: execution fkl5b63e2atpci888j3vrih9fgk; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution
    ADD CONSTRAINT fkl5b63e2atpci888j3vrih9fgk FOREIGN KEY (status_id) REFERENCES athena_tms.status(id);


--
-- Name: item fklgtnomwfllo9wtrosb0qoffh5; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fklgtnomwfllo9wtrosb0qoffh5 FOREIGN KEY (priority_id) REFERENCES athena_tms.priority(id);


--
-- Name: status_transition fkm8e9n1uv6mwl0c72e3bsxu46o; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition
    ADD CONSTRAINT fkm8e9n1uv6mwl0c72e3bsxu46o FOREIGN KEY (author) REFERENCES athena_core."user"(id);


--
-- Name: sync_info fknnvsxyu0mqffcgs3lbxhloite; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.sync_info
    ADD CONSTRAINT fknnvsxyu0mqffcgs3lbxhloite FOREIGN KEY (project_id) REFERENCES athena_core.project(id);


--
-- Name: item fkpcquid9hq5dwxrsg5lncnl1rt; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item
    ADD CONSTRAINT fkpcquid9hq5dwxrsg5lncnl1rt FOREIGN KEY (updated_by) REFERENCES athena_core."user"(id);


--
-- Name: execution fkpp38bat6onygn2egn4qvy6isw; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution
    ADD CONSTRAINT fkpp38bat6onygn2egn4qvy6isw FOREIGN KEY (cycle_id) REFERENCES athena_tms.cycle(id);


--
-- Name: status_transition fkqx786lpsnejbs8luvrii363xq; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.status_transition
    ADD CONSTRAINT fkqx786lpsnejbs8luvrii363xq FOREIGN KEY (from_status) REFERENCES athena_tms.status(id);


--
-- Name: execution fksxj934bma70rv4nxhl9jks1tg; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.execution
    ADD CONSTRAINT fksxj934bma70rv4nxhl9jks1tg FOREIGN KEY (executor_id) REFERENCES athena_core."user"(id);


--
-- Name: item_version_mid fktjjhljwefqu2xoqxaoxiv6jwt; Type: FK CONSTRAINT; Schema: athena_tms; Owner: postgres
--

ALTER TABLE ONLY athena_tms.item_version_mid
    ADD CONSTRAINT fktjjhljwefqu2xoqxaoxiv6jwt FOREIGN KEY (version_id) REFERENCES athena_core.app_version(id);


--
-- PostgreSQL database dump complete
--

