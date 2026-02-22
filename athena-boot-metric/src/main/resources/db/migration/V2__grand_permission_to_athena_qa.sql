GRANT USAGE ON SCHEMA athena_metric to athena_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA athena_metric to athena_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA athena_metric GRANT SELECT ON TABLES TO athena_ro;
