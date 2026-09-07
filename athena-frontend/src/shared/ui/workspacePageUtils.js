const numberFormatters = new Map();

export const emptyPage = {
  content: [],
  number: 0,
  size: 10,
  totalElements: 0,
  totalPages: 0,
};

export function buildFilterParams(filters = {}) {
  const params = new URLSearchParams();

  for (const [key, value] of Object.entries(filters)) {
    if (typeof value === "string" && value.trim()) {
      params.set(key, value.trim());
    }
  }

  return params;
}

export function buildResourcePath(path, filters = {}) {
  const queryString = buildFilterParams(filters).toString();
  return queryString ? `${path}?${queryString}` : path;
}

export function buildPagedResourcePath(path, options) {
  const params = buildFilterParams(options.filters);
  params.set("page", String(options.pageIndex ?? 0));
  params.set("size", String(options.pageSize ?? 10));
  params.set("sort", options.sort);
  params.set("direction", options.direction ?? "ASC");

  return `${path}?${params.toString()}`;
}

export function getFiltersFromSearchParams(searchParams, filters) {
  return Object.fromEntries(filters.map((filter) => [filter.key, searchParams.get(filter.key) ?? ""]));
}

export function getPageIndexFromSearchParams(searchParams) {
  const pageIndex = Number(searchParams.get("page"));

  return Number.isInteger(pageIndex) && pageIndex >= 0 ? pageIndex : 0;
}

export function buildWorkspaceSearchParams(filters = {}, pageIndex = 0) {
  const params = buildFilterParams(filters);

  if (pageIndex > 0) {
    params.set("page", String(pageIndex));
  }

  return params;
}

export function parsePagePayload(payload) {
  const pageInfo = payload?.page ?? payload ?? {};
  const content = Array.isArray(payload?.content) ? payload.content : [];

  return {
    content,
    number: pageInfo.number ?? payload?.number ?? 0,
    size: pageInfo.size ?? payload?.size ?? content.length,
    totalElements: pageInfo.totalElements ?? payload?.totalElements ?? content.length,
    totalPages: pageInfo.totalPages ?? payload?.totalPages ?? (content.length > 0 ? 1 : 0),
  };
}

export function getInitialFilters(filters) {
  return Object.fromEntries(filters.map((filter) => [filter.key, ""]));
}

export function normalizeFilters(filters) {
  return Object.fromEntries(Object.entries(filters).map(([key, value]) => [key, typeof value === "string" ? value.trim() : value]));
}

export function getErrorMessage(error) {
  if (error?.message) {
    return error.message;
  }

  return "Unable to load data through the gateway.";
}

function getNumberFormatter(maximumFractionDigits) {
  if (!numberFormatters.has(maximumFractionDigits)) {
    numberFormatters.set(
      maximumFractionDigits,
      new Intl.NumberFormat(undefined, {
        maximumFractionDigits,
      }),
    );
  }

  return numberFormatters.get(maximumFractionDigits);
}

export function formatDateTime(value) {
  return value ? new Date(value).toLocaleString() : "—";
}

export function formatNumber(value, maximumFractionDigits = 0, fallback = "—") {
  if (value == null) {
    return fallback;
  }

  const numericValue = Number(value);
  if (Number.isNaN(numericValue)) {
    return fallback;
  }

  return getNumberFormatter(maximumFractionDigits).format(numericValue);
}

export function formatDuration(value, maximumFractionDigits = 0) {
  return value == null ? "—" : `${formatNumber(value, maximumFractionDigits, "0")} ms`;
}
