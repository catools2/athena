-- Seed a handful of users and their aliases.
--
-- Athena identifies a person by a single canonical username and resolves every
-- other spelling through athena_core.user_alias, so the same human arriving from
-- Jira as a display name, from Scale as an account name and from git as a commit
-- author collapses to one row instead of three.
--
-- THIS IS SAMPLE DATA. It exists to show the shape and to give a local demo
-- database something to group by; replace it with your own directory before
-- running it anywhere real.
--
-- Note the id values in the alias insert assume an empty table - user ids are
-- serial, so run this on a fresh database or substitute the real ids.

INSERT INTO athena_core."user" (username)
VALUES ('jane doe'),
       ('alex smith'),
       ('sam taylor');

INSERT INTO athena_core.user_alias (user_id, alias)
VALUES (1, 'jane doe'),
       (1, 'jdoe'),
       (1, 'jane.doe'),
       (2, 'alex smith'),
       (2, 'asmith'),
       (3, 'sam taylor'),
       (3, 'staylor');
