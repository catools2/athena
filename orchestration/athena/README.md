![Athena](docs/img.png)

# Install Charts

## Tools

To install chart you need

* Docker Desktop WIth k8s enabled
* Helm 

## Installation Sequence

Start local registry

```shell 
docker run -d -p 5000:5000 --restart=always --name local-registry registry:2
```

Build docker images and push them to local registry. 
> you need to push images everytime you start local registry container. Z

```shell 
./mvnw clean package docker:build -DskipTests
```

Install helm charts

```shell 
cd orchestration/athena
./install.sh
```

## Availability

`default.replicas` is 2 and `podDisruptionBudget.enabled` is true. The two go together: a
single replica means every rollout, node drain and eviction leaves the component with zero
ready endpoints, and a Service in that state produces a kube-proxy REJECT that callers see
as a 500 rather than a retryable blip. The PDB (`maxUnavailable: 1`) stops a drain from
taking both replicas at once. It is rendered only for components with more than one replica,
because a PDB over a single-replica Deployment blocks drains outright instead of protecting
availability.

Each component also gets a startup and a readiness probe from `default.startupProbe` /
`default.readinessProbe`. Startup absorbs the ~40s Spring Boot boot so the liveness clock
does not start until the app is up; readiness includes the datasource, so a pod whose
connection pool is dead leaves the Service endpoints instead of black-holing traffic.
Liveness deliberately excludes the datasource - a database outage must not restart every pod
at once. Override either per component under `components.<name>`.

## Ad-hoc SQL endpoint

`athena-core` can expose `GET /query/record` and `GET /query/records`, which execute SQL the
caller supplies verbatim. **It is disabled by default and that is the right setting for most
deployments** - there is no allowlist and the statement cannot be parameterised, so turning
it on trusts every caller that can reach the service with read access to everything the
`athena_ro` role can see. Put it behind authentication and a NetworkPolicy first.

To enable it, set on the `athena-core` component:

```yaml
env:
  - name: ATHENA_QUERY_ENABLED
    value: "true"
```

When enabled it opens its own small pool as `athena_ro`, which is granted only `SELECT`, so
writes are refused by the database regardless of what is sent. `ATHENA_QUERY_STATEMENT_TIMEOUT_MS`
(default 30000) and `ATHENA_QUERY_MAX_ROWS` (default 10000) bound a single request.
