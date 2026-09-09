import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const gatewayProxyTarget = process.env.ATHENA_GATEWAY_PROXY ?? "http://localhost:8080";

export default defineConfig({
  base: "/ui/",
  plugins: [react()],
  server: {
    host: "0.0.0.0",
    port: 5173,
    strictPort: true,
    proxy: {
      "/agent": gatewayProxyTarget,
      "/analytics": gatewayProxyTarget,
      "/core": gatewayProxyTarget,
      "/git": gatewayProxyTarget,
      "/kube": gatewayProxyTarget,
      "/metric": gatewayProxyTarget,
      "/pipeline": gatewayProxyTarget,
      "/spec": gatewayProxyTarget,
      "/tms": gatewayProxyTarget,
    },
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
