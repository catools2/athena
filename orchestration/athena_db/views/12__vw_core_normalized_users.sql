-- athena.vw_core_normalized_users
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Maps opaque Jira account ids onto readable names.
--
-- Jira hands out identifiers like `jirauser10001` for accounts created after the
-- move to GDPR-safe usernames, and those ids are what the ETL stores. Reporting
-- that groups by author is unreadable against them, so this view carries the
-- site's own id -> name mapping.
--
-- THIS IS SITE-SPECIFIC DATA AND IS MEANT TO BE EDITED. The two rows below are
-- placeholders that show the shape; replace them with your own directory. Ids not
-- listed fall through to the stored username unchanged, so an incomplete mapping
-- degrades to today's behaviour rather than losing rows.
--
-- Prefer the user_alias table where you can: TranslatorHelper already records the
-- Jira display name as an alias at ingest time, which keeps the mapping current
-- without a hand-maintained CASE. This view exists for historical rows ingested
-- before that, and for accounts Jira no longer resolves.

DROP VIEW IF EXISTS athena.vw_core_normalized_users CASCADE;

CREATE OR REPLACE VIEW athena.vw_core_normalized_users AS
SELECT id,
       CASE username
           WHEN 'jirauser10001'::text THEN 'jane doe'::character varying
           WHEN 'jirauser10002'::text THEN 'alex smith'::character varying
           ELSE username
       END AS username
FROM athena_core."user";
