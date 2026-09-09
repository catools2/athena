-- Materialized views are snapshots, and PostgreSQL does not record when one was last
-- refreshed. Without that, a dashboard cannot tell a quiet week from a stalled refresh
-- job - it just plots old numbers as though they were current. pg_stat_all_tables.last_analyze
-- is the usual proxy, but it only moves when something analyzes the view and says nothing
-- about whether the refresh succeeded.
--
-- So the refresh records its own outcome.

CREATE TABLE IF NOT EXISTS athena.view_refresh_log (
    view_name    text PRIMARY KEY,
    refreshed_at timestamptz NOT NULL,
    duration_ms  bigint      NOT NULL,
    concurrent   boolean     NOT NULL,
    status       text        NOT NULL,
    message      text
);

COMMENT ON TABLE athena.view_refresh_log IS
    'Last refresh outcome per materialized view, written by athena.refresh_analytics_views().';

GRANT SELECT ON athena.view_refresh_log TO athena_ro;

-- Refresh every materialized view in the athena schema, newest dependency last.
--
-- CONCURRENTLY needs a unique index, and only some of these views have one. The rest take an
-- ACCESS EXCLUSIVE lock for the duration of the refresh, which blocks every dashboard reading
-- them - including mv_test_cycle_statistics and mv_inventory_trend, two of the heaviest. That is
-- why the mode is recorded per view rather than assumed: a slow non-concurrent refresh is a
-- visible cause of dashboard stalls, and you want to be able to see which ones they were.
--
-- Order is derived from pg_depend, not from the view names. These matviews read each other
-- through the plain mvw_/vw_ wrapper views, so the graph has to be walked through both kinds:
-- mv_inventory_trend -> mvw_inventory_trend -> mv_item_transitions -> mvw_item_transitions ->
-- mv_items. Refreshing alphabetically puts mv_item_transitions before mv_items and leaves every
-- dependent view exactly one cycle stale, permanently - which looks like working software.
--
-- Failures are recorded and do not abort the run; one broken view should not leave the other
-- seventeen stale.
-- Dropped first: CREATE OR REPLACE cannot change a function's OUT-parameter signature, so
-- replacing this later would fail with "cannot change return type of existing function".
DROP FUNCTION IF EXISTS athena.refresh_analytics_views();

CREATE FUNCTION athena.refresh_analytics_views()
    -- Result columns are named apart from view_refresh_log's own columns on purpose: inside
    -- plpgsql an OUT parameter shadows a same-named table column, and ON CONFLICT (view_name)
    -- cannot be qualified to disambiguate it.
    RETURNS TABLE(refreshed_view text, took_ms bigint, used_concurrent boolean, outcome text)
    LANGUAGE plpgsql
    SECURITY DEFINER
    SET search_path = athena, pg_catalog
AS $$
DECLARE
    target        record;
    started       timestamptz;
    elapsed_ms    bigint;
    use_concurrent boolean;
    result_status text;
    detail        text;
BEGIN
    FOR target IN
        WITH RECURSIVE rels AS (
            SELECT c.oid, c.relname, c.relkind
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE n.nspname = 'athena' AND c.relkind IN ('m', 'v')
        ),
        edges AS (
            SELECT DISTINCT r.ev_class AS dependent, d.refobjid AS source
            FROM pg_depend d
            JOIN pg_rewrite r ON r.oid = d.objid
            WHERE d.classid = 'pg_rewrite'::regclass
              AND d.refclassid = 'pg_class'::regclass
              AND r.ev_class <> d.refobjid
              AND r.ev_class IN (SELECT oid FROM rels)
              AND d.refobjid IN (SELECT oid FROM rels)
        ),
        walk AS (
            SELECT oid AS root, oid AS cur, 0 AS lvl, ARRAY[oid] AS seen FROM rels
            UNION ALL
            SELECT w.root, e.source, w.lvl + 1, w.seen || e.source
            FROM walk w
            JOIN edges e ON e.dependent = w.cur
            -- The cycle guard is belt and braces: a view graph should be acyclic, but a
            -- recursive CTE that meets a cycle does not terminate.
            WHERE NOT e.source = ANY(w.seen) AND w.lvl < 30
        )
        SELECT r.relname AS name,
               EXISTS (SELECT 1 FROM pg_index i
                       WHERE i.indrelid = r.oid AND i.indisunique) AS has_unique_index
        FROM rels r
        JOIN walk w ON w.root = r.oid
        WHERE r.relkind = 'm'
        GROUP BY r.oid, r.relname
        ORDER BY max(w.lvl), r.relname
    LOOP
        started := clock_timestamp();
        use_concurrent := target.has_unique_index;
        result_status := 'ok';
        detail := NULL;

        BEGIN
            IF use_concurrent THEN
                EXECUTE format('REFRESH MATERIALIZED VIEW CONCURRENTLY athena.%I', target.name);
            ELSE
                EXECUTE format('REFRESH MATERIALIZED VIEW athena.%I', target.name);
            END IF;
        EXCEPTION WHEN OTHERS THEN
            result_status := 'failed';
            detail := SQLERRM;
        END;

        elapsed_ms := (EXTRACT(EPOCH FROM clock_timestamp() - started) * 1000)::bigint;

        INSERT INTO athena.view_refresh_log AS l
            (view_name, refreshed_at, duration_ms, concurrent, status, message)
        VALUES (target.name, clock_timestamp(), elapsed_ms, use_concurrent, result_status, detail)
        ON CONFLICT (view_name) DO UPDATE
            SET refreshed_at = excluded.refreshed_at,
                duration_ms  = excluded.duration_ms,
                concurrent   = excluded.concurrent,
                status       = excluded.status,
                message      = excluded.message;

        refreshed_view := target.name;
        took_ms := elapsed_ms;
        used_concurrent := use_concurrent;
        outcome := result_status;
        RETURN NEXT;
    END LOOP;
END;
$$;

COMMENT ON FUNCTION athena.refresh_analytics_views() IS
    'Refreshes every athena materialized view, recording outcome in athena.view_refresh_log.';
