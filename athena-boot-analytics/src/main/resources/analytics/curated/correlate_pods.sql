-- Pods seen during the window: what the change actually ran on.
--
-- mv_pod_basic_info keeps one row per pod snapshot with no deletion timestamp, so "alive during
-- the window" is approximated by creation before the window ended and a sync inside it. Naming
-- that here matters: it is a weaker claim than a true interval overlap.
SELECT p.name,
       p.namespace,
       p.app,
       p.version,
       p.phase,
       p.created_at,
       p.last_sync
FROM athena.mv_pod_basic_info p
WHERE p.created_at <= :timeTo
  AND (p.last_sync IS NULL OR p.last_sync >= :timeFrom)
  AND (:namespace IS NULL OR p.namespace = :namespace)
ORDER BY p.created_at DESC
LIMIT 300
