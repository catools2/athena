ALTER TABLE athena_core.app_version DROP CONSTRAINT app_version_code_key;
ALTER TABLE athena_core.app_version ADD CONSTRAINT app_version_code_key UNIQUE (code, project_id)

ALTER TABLE athena_core.environment DROP CONSTRAINT environment_code_key;
ALTER TABLE athena_core.environment ADD CONSTRAINT environment_code_key UNIQUE (code, project_id)