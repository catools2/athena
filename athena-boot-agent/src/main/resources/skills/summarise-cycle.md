# Summarise a test cycle

Produce a short readable status for one cycle.

1. `run_query` `cycles_list` to locate the cycle and read its rollup.
2. `run_query` `cycle_executions` for the detail.
3. Lead with what is blocking: failures first, then blocked, then unexecuted. Passing tests are
   the least interesting thing in a cycle summary.

Name specific test keys. "Four failures" is much less useful than naming them.
