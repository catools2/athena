-- athena.mvw_pod_basic_info
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_pod_basic_info CASCADE;

CREATE OR REPLACE VIEW athena.mvw_pod_basic_info AS
SELECT DISTINCT pod_rank,
                app,
                version,
                namespace,
                created_at,
                last_sync,
                project_id,
                status_id,
                phase,
                name
FROM
  (SELECT rank() OVER (PARTITION BY p.project_id, p.namespace, pla.value
                       ORDER BY p.last_sync DESC) AS sync_rank,
          rank() OVER (PARTITION BY p.project_id, pla.value
                       ORDER BY plv.value DESC) AS pod_rank,
          pla.value AS app,
          plv.value AS version,
          p.namespace,
          p.created_at,
          p.last_sync,
          p.project_id,
          p.status_id,
          ps.phase,
          p.name
   FROM athena_kube.pod p
   LEFT JOIN athena_kube.pod_label_mid plma ON plma.pod_id = p.id
   LEFT JOIN athena_kube.pod_label pla ON pla.id = plma.label_id
   LEFT JOIN athena_kube.pod_label_mid plmv ON plmv.pod_id = p.id
   LEFT JOIN athena_kube.pod_label plv ON plv.id = plmv.label_id
   LEFT JOIN athena_kube.pod_status ps ON ps.id = p.status_id
   WHERE pla.name::text = 'app.kubernetes.io/name'::text
     AND plv.name::text = 'app.kubernetes.io/version'::text) t
WHERE sync_rank = 1;
