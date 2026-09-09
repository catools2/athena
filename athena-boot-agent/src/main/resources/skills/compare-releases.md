# Compare two releases

Answer "is this release better or worse than the last one".

1. `list_queries` with a search for `cycle` to find the cycle statistics queries, then
   `run_query` `cycles_list` once per version.
2. Compare pass rate *and* total — a pass rate that improved because far fewer tests ran is not
   an improvement, and that is the most common way this question gets answered wrongly.
3. For timing, `run_query` `perf_regression` over the release window. It already compares
   against the preceding window of equal length and excludes actions with too few samples.

Report the counts you compared, not just the percentages.
