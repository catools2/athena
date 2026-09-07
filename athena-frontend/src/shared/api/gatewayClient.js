export const apiRoots = Object.freeze({
  core: "/core",
  git: "/git",
  kube: "/kube",
  metric: "/metric",
  pipeline: "/pipeline",
  spec: "/spec",
  tms: "/tms",
});

const validRoots = new Set(Object.values(apiRoots));

function normalizePath(path = "") {
  if (!path) {
    return "";
  }

  return path.startsWith("/") ? path : `/${path}`;
}

export class GatewayClientError extends Error {
  constructor(message, status, url) {
    super(message);
    this.name = "GatewayClientError";
    this.status = status;
    this.url = url;
  }
}

export function buildGatewayUrl(root, path = "") {
  if (!validRoots.has(root)) {
    throw new Error(`Unsupported gateway root: ${root}`);
  }

  return `${root}${normalizePath(path)}`;
}

export async function requestJson(root, path = "", init = {}) {
  const url = buildGatewayUrl(root, path);
  const headers = {
    Accept: "application/json",
    ...(init.headers ?? {}),
  };

  const response = await fetch(url, {
    ...init,
    headers,
  });

  if (response.status === 204) {
    return null;
  }

  if (!response.ok) {
    throw new GatewayClientError(`Gateway request failed with ${response.status}.`, response.status, url);
  }

  const contentType = response.headers.get("content-type") ?? "";
  if (contentType.includes("application/json")) {
    return response.json();
  }

  return response.text();
}
