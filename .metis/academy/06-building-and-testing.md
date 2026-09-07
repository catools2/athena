---
topics: athena-practice
---

# 6 · Building and testing

## What a local build needs

Maven 3.8.6+, JDK 21, and Docker.

```shell
./mvnw clean compile -U
./mvnw install -DskipTests
./mvnw package docker:build -DskipTests
```

Docker is not optional tooling here — it is how the tests get a database, which
is the next section.

## Tests run against a real database, started for them

Athena's functional tests do not mock persistence. JUnit drives Testcontainers,
which pulls the latest **golden database** image from the registry and starts
it; Spring then boots against that database, and the tests execute.

The sequence has a second half that is easy to miss: after the functional tests
run, the golden DB is stopped, a **fresh** set of golden containers is started,
and the application is **restarted against the new database** before teardown.

## Why the restart is there

Starting an application against a database that a previous test run has already
written to is a different test from starting against a clean one. The second
boot exercises the path a real deployment takes — an application meeting a
database it did not create — and a schema or migration assumption that only
holds on a freshly-seeded database fails there rather than in production.

## What this costs you

A test run needs a working Docker registry login and pulls an image. A failure
at the Testcontainers stage is an environment problem, not a test failure, and
the two look similar in the output — check that the golden DB image resolved
before reading the assertion.
