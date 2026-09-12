# Moved to catools2/atlas

The browser surface left this monorepo. It is now its own repository, serving
**both** Athena and Métis — Athena as the data plane, Métis as the reasoning
plane — because from a user's point of view they are one product.

    https://github.com/catools2/atlas

The seven commits that touched `athena-frontend/` came across with it; run
`git log -- athena-frontend` here for the monorepo-side view.

## Nothing in this repo needs to change

The gateway contract is unchanged. `athena-gateway` still owns the route and
still reads the same property:

    athena.frontend.uri: ${ATHENA_FRONTEND_URI:http://localhost:4173}
    /  and  /ui  →  302 /ui/
    /ui/**       →  the frontend

Point `ATHENA_FRONTEND_URI` at wherever the new repo runs. The frontend was in
neither this repo's CI nor its Maven build, so nothing else referenced it.

This directory holds only this note, so that `cd athena-frontend` answers the
question rather than looking like a mistake.
