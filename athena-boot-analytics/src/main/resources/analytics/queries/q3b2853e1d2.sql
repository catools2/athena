select
  version,
  sum(case when trim(environment_set ->> 0) = 'Production' then 1 else 0 end) as prod,
  count(*) AS total
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(affected_version_set) AS version
WHERE i.project_code = 'DEMO' AND i.item_type = 'Bug'
and version in ('4.3','4.4','4.5','4.6','4.7','4.8','4.9','4.10.0','4.11.0','4.12.0','4.13.0','4.14.0','4.15.0')
GROUP by version
ORDER BY string_to_array(version, '.')::int[]
