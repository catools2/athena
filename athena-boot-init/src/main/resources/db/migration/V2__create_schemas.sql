-- Create main athena schema
CREATE SCHEMA IF NOT EXISTS athena;

-- Create service-specific schemas
CREATE SCHEMA IF NOT EXISTS athena_core;
CREATE SCHEMA IF NOT EXISTS athena_git;
CREATE SCHEMA IF NOT EXISTS athena_kube;
CREATE SCHEMA IF NOT EXISTS athena_metric;
CREATE SCHEMA IF NOT EXISTS athena_openapi;
CREATE SCHEMA IF NOT EXISTS athena_tms;
CREATE SCHEMA IF NOT EXISTS athena_pipeline;

-- Grant usage on main athena schema to all roles
GRANT USAGE ON SCHEMA athena TO athena_ro, athena_core_user, athena_git_user, athena_kube_user, athena_metric_user, athena_openapi_user, athena_tms_user, athena_pipeline_user;

-- Grant usage on service schemas to respective roles (and read-only role)
GRANT USAGE ON SCHEMA athena_core TO athena_ro;
GRANT USAGE ON SCHEMA athena_git TO athena_ro;
GRANT USAGE ON SCHEMA athena_kube TO athena_ro;
GRANT USAGE ON SCHEMA athena_metric TO athena_ro;
GRANT USAGE ON SCHEMA athena_openapi TO athena_ro;
GRANT USAGE ON SCHEMA athena_tms TO athena_ro;
GRANT USAGE ON SCHEMA athena_pipeline TO athena_ro;
