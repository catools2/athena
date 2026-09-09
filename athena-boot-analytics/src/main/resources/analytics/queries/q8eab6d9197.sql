select distinct p.name
From athena_pipeline.pipeline p
    JOIN athena_core.environment env ON p.environment_id = env.id
    LEFT JOIN athena_core.app_version v ON p.version_id = v.id
    LEFT JOIN athena_core.project proj ON v.project_id = proj.id
    WHERE proj.code::text = 'DEMO'::text
      AND p.start_date between :timeFrom and :timeTo
      AND p.name::text ~~ 'Baseline Tests - %'::text;
