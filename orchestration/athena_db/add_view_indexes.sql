-- Indexes for athena materialized views.
--
-- WHY THIS FILE EXISTS
-- --------------------
-- A materialized view with no unique index cannot be refreshed with
-- REFRESH MATERIALIZED VIEW CONCURRENTLY. The non-concurrent form takes an
-- ACCESS EXCLUSIVE lock for the entire duration of the refresh, so every
-- Grafana panel and every application query against that view blocks - and a
-- blocked session holds its connection. That is what turns a slow refresh into
-- Hikari "Pool is empty" errors and a connection pile-up on the database.
--
-- For an in-place upgrade, run this file with psql in autocommit mode after
-- the materialized views exist, then switch the refresh job to use
-- REFRESH MATERIALIZED VIEW CONCURRENTLY. Do not wrap this file in a
-- transaction: PostgreSQL forbids CREATE INDEX CONCURRENTLY there.
--
-- Full view rebuilds use views/*.sql, which recreate these indexes immediately
-- after recreating each materialized view.

-- ---------------------------------------------------------------------------
-- Unique indexes (required for CONCURRENTLY)
-- ---------------------------------------------------------------------------

-- mvw_item_metadata is GROUP BY imm.item_id -> exactly one row per item_id.
CREATE UNIQUE INDEX IF NOT EXISTS mv_item_metadata_item_id_uidx
  ON athena.mv_item_metadata (item_id);

-- mvw_items joins athena_tms.item to status/project/priority/type (all
-- many-to-one on item) plus mv_item_metadata (1:1 on item_id) and the
-- item_versions CTE (GROUP BY item_id, so also 1:1). One row per item.
CREATE UNIQUE INDEX IF NOT EXISTS mv_items_item_id_uidx
  ON athena.mv_items (item_id);

-- Legacy reporting snapshots refreshed by the analytics materialized-view pipeline.
-- These indexes use stable identities from the underlying view definitions and
-- allow REFRESH MATERIALIZED VIEW CONCURRENTLY without blocking readers.
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS mv_api_path_info_matching_id_uidx
  ON athena.mv_api_path_info_with_matching_pattern (id);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS mv_pipeline_product_statistic_key_uidx
  ON athena.mv_pipeline_product_statistic
    (project, environment, pipeline_name, pipeline_number, start_date);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS mv_pod_basic_info_row_uidx
  ON athena.mv_pod_basic_info
    (pod_rank, app, version, namespace, created_at, last_sync, project_id, status_id, phase, name);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS mv_product_team_scale_link_key_uidx
  ON athena.mv_product_team_scale_link (item_id, scale_key);

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS mv_tested_api_path_info_key_uidx
  ON athena.mv_tested_api_path_info (method, url, internal);

-- ---------------------------------------------------------------------------
-- Supporting indexes for the Grafana access patterns
-- ---------------------------------------------------------------------------
-- Across grafana/onprem/*.json the dominant predicates on mv_items are
-- project_code (22x), item_type (21x), item_status (3x) and item_key (5x).
-- Without these every panel is a sequential scan of the full view.

CREATE INDEX IF NOT EXISTS mv_items_project_type_status_idx
  ON athena.mv_items (project_code, item_type, item_status);

CREATE INDEX IF NOT EXISTS mv_items_item_key_idx
  ON athena.mv_items (item_key);

-- ---------------------------------------------------------------------------
-- Remaining materialized views - NOT YET INDEXED
-- ---------------------------------------------------------------------------
-- The unique key for the views below could not be proven from their
-- definitions alone, so they are deliberately left out rather than guessed at.
-- For each one, confirm the candidate key returns zero rows:
--
--   SELECT <cols>, count(*) FROM athena.<view>
--   GROUP BY <cols> HAVING count(*) > 1 LIMIT 1;
--
-- then add:  CREATE UNIQUE INDEX ON athena.<view> (<cols>);
--
--   mv_api_coverage
--   mv_ci_run_pipeline_execution
--   mv_execution_info
--   mv_inventory_trend
--   mv_item_transitions
--   mv_pipeline_execution
--   mv_pipeline_execution_info
--   mv_pipeline_execution_test_id
--   mv_scenario_execution
--   mv_product_sql_metrics
--   mv_regression_automation_to_manual_ratio
--   mv_review_candidate_test
--   mv_test_cycle_statistics
--
-- Priority order by Grafana usage: mv_test_cycle_statistics (63 panel refs),
-- mv_inventory_trend (9), mv_pipeline_execution_info (3),
-- mv_review_candidate_test (2), mv_regression_automation_to_manual_ratio (1).
