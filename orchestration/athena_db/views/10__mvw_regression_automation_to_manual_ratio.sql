-- athena.mvw_regression_automation_to_manual_ratio
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_regression_automation_to_manual_ratio CASCADE;

CREATE OR REPLACE VIEW athena.mvw_regression_automation_to_manual_ratio AS
SELECT DISTINCT project_code,
                version,
                sum(automated) AS automated,
                sum(playwright) AS playwright,
                sum(sme) AS sme,
                sum(manual) AS manual,
                sum(golden) AS golden
FROM
  (SELECT DISTINCT t1.item_id,
                   t1.project_code,
                   t1.version,
                   t1.automated,
                   t1.playwright,
                   t1.sme,
                   t1.manual,
                   t1.golden
   FROM
     (SELECT e.item_id,
             p.code AS project_code,
             v.name AS version,
             c.name AS cycle,
             CASE
                 WHEN c.name::text ~~ '%SME%'::text THEN 1
                 ELSE 0
             END AS sme,
             CASE
                 WHEN c.name::text ~~ '%Golden%'::text THEN 1
                 ELSE 0
             END AS golden,
             CASE
                 WHEN c.name::text ~~ '%Playwright%'::text THEN 1
                 ELSE 0
             END AS playwright,
             CASE
                 WHEN c.name::text ~~ '%Automated%'::text
                      AND c.name::text !~~ '%Golden%'::text THEN 1
                 ELSE 0
             END AS automated,
             CASE
                 WHEN c.name::text !~~ '%Playwright%'::text
                      AND c.name::text !~~ '%Automated%'::text
                      AND c.name::text !~~ '%Golden%'::text
                      AND c.name::text !~~ '%SME%'::text THEN 1
                 ELSE 0
             END AS manual
      FROM athena_tms.cycle c
      LEFT JOIN athena_tms.execution e ON e.cycle_id = c.id
      LEFT JOIN athena_core.app_version v ON v.id = c.version_id
      LEFT JOIN athena_core.project p ON p.id = v.project_id
      WHERE c.name::text ~~ '%Regression%'::text
      GROUP BY e.item_id,
               p.code,
               v.name,
               c.name) t1) t
GROUP BY project_code,
         version;
