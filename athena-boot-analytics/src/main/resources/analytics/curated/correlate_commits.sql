-- Commits landed in the window. The "what changed" half of a correlation.
SELECT c.hash,
       c.short_message,
       r.name          AS repository,
       u.username      AS author,
       c.commit_time,
       c.total_file    AS files_changed,
       c.line_inserted AS lines_added,
       c.line_deleted  AS lines_removed
FROM athena_git.commit c
JOIN athena_git.repository r ON r.id = c.repository_id
LEFT JOIN athena_core."user" u ON u.id = c.author_id
WHERE c.commit_time BETWEEN :timeFrom AND :timeTo
  AND (cardinality(:repository) = 0 OR r.name = ANY(:repository))
ORDER BY c.commit_time DESC
LIMIT 500
