# Explain a test failure

Work out why a test or cycle went red, and say what changed around it.

1. `run_query` `cycle_executions` for the cycle, or `test_history` for a single test key.
   A test that has failed before is a different problem from one that just started failing —
   check the history before blaming the latest change.
2. Take the timestamp of the first failure and call `correlate_change` for a window ending
   there. Six hours back is usually enough; widen it if nothing landed.
3. Read the commits and pipeline runs in that window against the timing section. A change that
   moved p95 and a test that started timing out are probably the same story.

Say plainly when the evidence does not support a conclusion. "Three commits landed in the hour
before it started failing, none obviously related" is a useful answer; inventing a cause is not.
