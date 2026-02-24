-- Create read-only user with database-level access
DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_ro') THEN
		CREATE ROLE athena_ro LOGIN PASSWORD 'athena_ro';
        GRANT CONNECT ON DATABASE athena TO athena_ro;
	END IF;
END
$$;

-- Create service-specific users for JPA interactions
DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_core_user') THEN
		CREATE ROLE athena_core_user LOGIN PASSWORD 'athena_core_user';
        GRANT CONNECT ON DATABASE athena TO athena_core_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_git_user') THEN
		CREATE ROLE athena_git_user LOGIN PASSWORD 'athena_git_user';
        GRANT CONNECT ON DATABASE athena TO athena_git_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_kube_user') THEN
		CREATE ROLE athena_kube_user LOGIN PASSWORD 'athena_kube_user';
        GRANT CONNECT ON DATABASE athena TO athena_kube_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_metric_user') THEN
		CREATE ROLE athena_metric_user LOGIN PASSWORD 'athena_metric_user';
        GRANT CONNECT ON DATABASE athena TO athena_metric_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_openapi_user') THEN
		CREATE ROLE athena_openapi_user LOGIN PASSWORD 'athena_openapi_user';
        GRANT CONNECT ON DATABASE athena TO athena_openapi_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_tms_user') THEN
		CREATE ROLE athena_tms_user LOGIN PASSWORD 'athena_tms_user';
        GRANT CONNECT ON DATABASE athena TO athena_tms_user;
	END IF;
END
$$;

DO $$
BEGIN
	IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'athena_pipeline_user') THEN
		CREATE ROLE athena_pipeline_user LOGIN PASSWORD 'athena_pipeline_user';
        GRANT CONNECT ON DATABASE athena TO athena_pipeline_user;
	END IF;
END
$$;
