export const reportTimeWindowOptions = [
  { key: "all", label: "All buckets" },
  { key: "1d", label: "1 day" },
  { key: "3d", label: "3 days" },
  { key: "7d", label: "7 days" },
];

export const reportHealthOptions = [
  { key: "all", label: "All health states" },
  { key: "healthy", label: "Healthy" },
  { key: "watch", label: "Watch" },
  { key: "attention", label: "Attention" },
];

const timeWindowToDays = {
  "1d": 1,
  "3d": 3,
  "7d": 7,
};

function buildDownloadLink(fileName, mimeType, content) {
  if (typeof document === "undefined") {
    return;
  }

  const downloadLink = document.createElement("a");
  downloadLink.href = `data:${mimeType},${encodeURIComponent(content)}`;
  downloadLink.download = fileName;
  downloadLink.click();
}

function escapeCsvValue(value) {
  const normalizedValue = value == null ? "" : String(value);
  if (!/[",\n]/.test(normalizedValue)) {
    return normalizedValue;
  }

  return `"${normalizedValue.replace(/"/g, '""')}"`;
}

export function buildReportPath(path, params = {}) {
  const query = new URLSearchParams();

  Object.entries(params).forEach(([key, value]) => {
    if (value === undefined || value === null || value === "") {
      return;
    }

    query.set(key, String(value));
  });

  const queryString = query.toString();
  return queryString ? `${path}?${queryString}` : path;
}

function getGitFreshnessForHealth(healthState) {
  if (healthState === "attention") {
    return "STALE";
  }

  if (healthState === "watch") {
    return "AGING";
  }

  return "";
}

function getSpecFreshnessForHealth(healthState) {
  if (healthState === "attention") {
    return "STALE";
  }

  if (healthState === "watch") {
    return "AGING";
  }

  return "";
}

function getPipelineStateForHealth(healthState) {
  if (healthState === "attention" || healthState === "watch") {
    return "RUNNING";
  }

  return "";
}

function getQualityProgressForHealth(healthState) {
  if (healthState === "attention" || healthState === "watch") {
    return "PENDING";
  }

  return "";
}

export function buildWorkspaceHandoffPath(path, healthState = "all") {
  if (path === "/apis/specs") {
    return buildReportPath(path, { freshness: getSpecFreshnessForHealth(healthState) });
  }

  if (path === "/git/repositories") {
    return buildReportPath(path, { freshness: getGitFreshnessForHealth(healthState) });
  }

  if (path === "/pipelines/runs") {
    return buildReportPath(path, { state: getPipelineStateForHealth(healthState) });
  }

  if (path === "/quality/executions") {
    return buildReportPath(path, { progress: getQualityProgressForHealth(healthState) });
  }

  return path;
}

export function getQueryOptionValue(searchParams, key, options, fallback) {
  const allowedValues = new Set(options.map((option) => option.key));
  const value = searchParams.get(key);
  return value && allowedValues.has(value) ? value : fallback;
}

export function buildUpdatedSearchParams(searchParams, updates, defaults = {}) {
  const next = new URLSearchParams(searchParams);

  Object.entries(updates).forEach(([key, value]) => {
    if (value == null || value === "" || value === defaults[key]) {
      next.delete(key);
      return;
    }

    next.set(key, String(value));
  });

  return next;
}

export function getTimeWindowDays(timeWindow) {
  return timeWindowToDays[timeWindow] ?? null;
}

export function filterTrendPoints(points = [], timeWindow = "all") {
  if (!Array.isArray(points) || points.length === 0 || timeWindow === "all") {
    return Array.isArray(points) ? points : [];
  }

  const dayWindow = timeWindowToDays[timeWindow];
  if (!dayWindow) {
    return points;
  }

  const latestTimestamp = points.reduce((latestValue, point) => {
    const timestamp = new Date(point.bucketStart ?? 0).getTime();
    return Number.isNaN(timestamp) ? latestValue : Math.max(latestValue, timestamp);
  }, 0);

  if (!latestTimestamp) {
    return points;
  }

  const threshold = latestTimestamp - dayWindow * 24 * 60 * 60 * 1000;
  return points.filter((point) => new Date(point.bucketStart ?? 0).getTime() >= threshold);
}

export function getTimeWindowLabel(timeWindow) {
  return reportTimeWindowOptions.find((option) => option.key === timeWindow)?.label ?? "All buckets";
}

export function matchesHealthFilter(healthState, healthFilter) {
  return healthFilter === "all" || healthState === healthFilter;
}

export function getHealthToneClass(healthState) {
  if (healthState === "attention") {
    return "status-chip--critical";
  }

  if (healthState === "watch") {
    return "status-chip--warning";
  }

  if (healthState === "healthy") {
    return "status-chip--success";
  }

  return "status-chip--neutral";
}

export function triggerReportPrint() {
  if (typeof window !== "undefined" && typeof window.print === "function") {
    window.print();
  }
}

export function downloadReportJson(fileName, payload) {
  buildDownloadLink(fileName, "application/json;charset=utf-8", JSON.stringify(payload, null, 2));
}

export function downloadReportCsv(fileName, columns, rows) {
  const header = columns.map((column) => escapeCsvValue(column.label)).join(",");
  const lines = rows.map((row) => columns.map((column) => escapeCsvValue(row[column.key])).join(","));
  buildDownloadLink(fileName, "text/csv;charset=utf-8", [header, ...lines].join("\n"));
}
