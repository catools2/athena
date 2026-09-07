-- Metadata values were capped at varchar(2000). Real ingested values exceed that,
-- and the insert in ItemServiceImpl.normalizeMetadata failed with
--   ERROR: value too long for type character varying(2000)
-- which poisoned the Hibernate session and surfaced as
--   HHH000099: Entry for instance of ItemMetadata has a null identifier
--
-- Widening alone is not sufficient. The table carries a UNIQUE constraint on
-- (name, value) backed by a btree index, and a btree index tuple has a hard
-- size ceiling. Making `value` unbounded while still indexing it directly
-- trades the length error for, verified on postgres:16 with a 9600-character
-- incompressible value:
--   ERROR: index row requires 9624 bytes, maximum size is 8191
-- That is a strictly worse failure than the one being fixed, because it depends
-- on payload entropy rather than a declared limit: the same length of highly
-- repetitive text is TOAST-compressed and inserts without complaint, so the
-- error would appear only for some values and be maddening to reproduce.
--
-- So the uniqueness guarantee moves onto md5(value), which is fixed-width and
-- therefore safe to index at any value length. This is an expression index, so
-- no column is added and the table is not rewritten. varchar -> text is itself
-- a no-rewrite change in PostgreSQL (the types are binary-coercible), keeping
-- this migration cheap to apply on a live table.

ALTER TABLE athena_tms.metadata
    ALTER COLUMN value TYPE text;

-- V10__tms_init.sql created the constraint as uk_metadata_name_value, while the
-- JPA @Table annotation named it uk_item_metadata_name_value. Drop either
-- spelling so this migration is safe against a schema built from the entity
-- rather than from V10.
ALTER TABLE athena_tms.metadata
    DROP CONSTRAINT IF EXISTS uk_metadata_name_value;
ALTER TABLE athena_tms.metadata
    DROP CONSTRAINT IF EXISTS uk_item_metadata_name_value;
DROP INDEX IF EXISTS athena_tms.idx_item_metadata_name_value;

-- Strictly equivalent to the old (name, value) uniqueness for every value that
-- could previously be stored, so no existing row can conflict.
CREATE UNIQUE INDEX IF NOT EXISTS uk_metadata_name_value_md5
    ON athena_tms.metadata (name, md5(value));
