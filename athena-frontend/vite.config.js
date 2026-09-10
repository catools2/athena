import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const gatewayProxyTarget = process.env.ATHENA_GATEWAY_PROXY ?? "http://localhost:8080";

/** Where the app is mounted, behind the gateway and in dev alike. */
const BASE = "/ui/";

/**
 * The prefixes the gateway owns. One list, used both to build the dev proxy and to decide what
 * the redirect below must keep its hands off - two lists would drift, and the failure would be a
 * service call answered with a 302 to an HTML page.
 */
const API_PREFIXES = [
  "/agent", "/analytics", "/core", "/git", "/kube",
  "/metric", "/pipeline", "/spec", "/tms",
];

/**
 * Send stray requests to the app's base instead of Vite's "did you mean to visit /ui/?" page.
 *
 * The app is served under /ui/ so its built asset URLs resolve behind the gateway, which means
 * the dev server's own root is not the app and Vite says so rather than guessing. The gateway
 * already answers that in production - `Path=/,/ui` with `RedirectTo=302,/ui/` in its route
 * table - so this is the same rule applied to the dev and preview servers, and typing a bare
 * host or pasting a link without the prefix lands on the app in every environment.
 *
 * Deliberately installed ahead of Vite's internal middleware, because the message being replaced
 * comes from that middleware; everything it must not touch is listed explicitly instead.
 */
function redirectToBase() {
  const passThrough = [...API_PREFIXES, "/@", "/node_modules/", "/src/", "/favicon.ico"];

  const middleware = (req, res, next) => {
    const [path, query] = (req.url ?? "/").split("?");
    if (path.startsWith(BASE) || passThrough.some((prefix) => path.startsWith(prefix))) {
      next();
      return;
    }
    // "/" and "/ui" are the base itself; anything else keeps its path so a deep link such as
    // /performance?range=30d survives the redirect intact.
    const suffix = path === "/" || path === "/ui" ? "" : path.replace(/^\/+/, "");
    res.writeHead(302, { Location: `${BASE}${suffix}${query ? `?${query}` : ""}` });
    res.end();
  };

  return {
    name: "athena-redirect-to-base",
    // Block bodies, not expression bodies: Vite treats a value returned from these hooks as a
    // post-hook to invoke later, and `middlewares.use()` returns the connect app itself.
    configureServer(server) {
      server.middlewares.use(middleware);
    },
    configurePreviewServer(server) {
      server.middlewares.use(middleware);
    },
  };
}

export default defineConfig({
  base: BASE,
  plugins: [react(), redirectToBase()],
  server: {
    host: "0.0.0.0",
    port: 5173,
    strictPort: true,
    proxy: Object.fromEntries(API_PREFIXES.map((prefix) => [prefix, gatewayProxyTarget])),
  },
  preview: {
    host: "0.0.0.0",
    port: 4173,
    strictPort: true,
  },
  test: {
    environment: "jsdom",
    globals: true,
    setupFiles: "./src/test/setup.js",
  },
});
