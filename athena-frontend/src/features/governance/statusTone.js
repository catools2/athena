export function getStatusToneClass(status) {
  const normalizedStatus = String(status ?? "").toLowerCase();

  if (["pass", "success", "done", "complete"].some((token) => normalizedStatus.includes(token))) {
    return "status-chip--success";
  }

  if (["fail", "blocked", "reject", "error"].some((token) => normalizedStatus.includes(token))) {
    return "status-chip--critical";
  }

  if (["progress", "running", "queue", "pending", "hold"].some((token) => normalizedStatus.includes(token))) {
    return "status-chip--warning";
  }

  return "status-chip--neutral";
}
