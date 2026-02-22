GRANT USAGE ON SCHEMA athena_openapi to athena_ro;
GRANT SELECT ON ALL TABLES IN SCHEMA athena_openapi to athena_ro;
ALTER DEFAULT PRIVILEGES IN SCHEMA athena_openapi GRANT SELECT ON TABLES TO athena_ro;
