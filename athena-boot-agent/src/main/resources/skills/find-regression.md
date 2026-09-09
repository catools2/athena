# Find the change behind a regression

1. `run_query` `perf_regression` for the window to see which actions slowed, ranked by absolute
   p95 delta. Relative percentages mislead on fast actions — a 3ms action doubling is rarely
   worth chasing.
2. `correlate_change` over the same window for the commits, runs and pods.
3. Check the pod list. A timing change that lines up with pods being replaced may be a cold
   start or a different node rather than a code change.

State the confidence you actually have. Correlation over one window is a lead, not a cause.
