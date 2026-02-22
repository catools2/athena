GRANT USAGE ON SCHEMA athena_pipeline to athena_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA athena_pipeline to athena_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA athena_pipeline GRANT SELECT ON TABLES TO athena_ro;
