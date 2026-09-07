-- athena.mvw_product_team_scale_link
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_product_team_scale_link CASCADE;

CREATE OR REPLACE VIEW athena.mvw_product_team_scale_link AS
SELECT DISTINCT imm.item_id,
                m.value AS scale_key
FROM athena_tms.item_metadata_mid imm
LEFT JOIN athena_tms.metadata m ON m.id = imm.metadata_id
WHERE m.name::text = 'Label'::text
  AND m.value::text ~~ 'DEMO-T%'::text;
