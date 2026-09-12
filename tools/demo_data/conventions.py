"""Every string the Athena analytics views and curated queries hardcode.

These are collected here rather than spelled inline at each use because they are not this
generator's choices - each one is a convention some view or query already depends on, and data
that misses one lands in its table and then fails to appear in the UI. That failure mode is
silent and expensive: the row is in PostgreSQL, the panel says there is nothing to show.

Every entry cites the file that imposes it.
"""

# --- Pipeline execution status ------------------------------------------------------------
#
# From athena-client-pipeline-testng PipelineListener.getStatus(), which is what a real TestNG
# run reports. correlate_pipeline_runs.sql counts a pass as exactly 'SUCCESS'. Note that
# athena-locust's pipeline_factory.py emits PASSED/FAILED/QUEUED instead - that factory does not
# match the real ETL and its output would be counted as failures.
PIPELINE_STATUSES = ["CREATED", "SUCCESS", "FAILURE", "SKIP", "STARTED", "NOT_SET"]
PIPELINE_PASS = "SUCCESS"
PIPELINE_FAIL = "FAILURE"
PIPELINE_SKIP = "SKIP"

# --- Test execution status ----------------------------------------------------------------
#
# From athena-client-atlassian-scale ScaleExecutionStatus, the vocabulary the Zephyr Scale ETL
# produces. mvw_test_cycle_statistics maps upper(name) onto its pass/fail/blocked columns, and
# the curated queries compare against the title-case original.
EXECUTION_STATUSES = ["Pass", "Fail", "Blocked", "In Progress", "Not Executed"]
EXEC_PASS = "Pass"
EXEC_FAIL = "Fail"
EXEC_BLOCKED = "Blocked"
EXEC_NOT_EXECUTED = "Not Executed"

# --- Kubernetes pod labels ------------------------------------------------------------------
#
# 06__mvw_pod_basic_info.sql inner-joins pod_label twice and requires BOTH of these names. A pod
# without them exists in athena_kube.pod and produces zero rows in athena.mv_pod_basic_info -
# which is the view correlate_pods.sql and change_coverage.sql read, so the UI would report that
# pods were never ingested at all.
POD_LABEL_NAME = "app.kubernetes.io/name"
POD_LABEL_VERSION = "app.kubernetes.io/version"

# --- Pipeline naming --------------------------------------------------------------------------
#
# 02__mvw_ci_run_pipeline_execution.sql selects only project.code = 'CI_RUN'.
CI_RUN_PROJECT_CODE = "CI_RUN"

# 30__mvw_pipeline_execution.sql excludes pipelines named like 'local%' and derives
# functional_group from a package name matching '%demo%.<group>%'.
PIPELINE_NAME_PREFIXES = ["build", "deploy", "regression", "release", "smoke"]
FUNCTIONAL_GROUPS = ["sanity", "api", "web", "batch"]


def execution_package(group: str) -> str:
    """A package name mvw_pipeline_execution can classify. The literal 'demo' is load-bearing."""
    return f"com.demo.{group}"


# --- TMS item metadata ------------------------------------------------------------------------
#
# 03__mvw_item_metadata.sql pivots these exact metadata names into the *_set jsonb columns that
# mv_items and mv_test_cycle_statistics expose - and that the frontend filters read. A team that
# is not recorded under the name 'Team' does not reach teams_set, so the team filter never
# offers it.
META_RCA = "RCA"
META_TEAM = "Team"
META_SCRUM_TEAM = "Scrum Team"
META_FUNCTIONAL_AREA = "Functional Area"
META_AFFECTED_VERSION = "Affected Version/s"
META_LABEL = "Label"

# 09__mvw_product_team_scale_link.sql and 15__vw_product_team_scale_link.sql match
# metadata name='Label' with value LIKE 'DEMO-T%'.
LABEL_VALUE_PREFIX = "DEMO-T"

# --- Reference codes ---------------------------------------------------------------------------
#
# StatusDto, PriorityDto and ItemTypeDto each carry a code and a name, and the code is capped at
# 10 characters. It matters which is which: an item or execution resolves its status *by code*
# ("No status with code In Progress found"), while mvw_test_cycle_statistics exposes s.name as
# execution_status, and that is what the curated queries compare against. So the name must stay
# exactly the Scale vocabulary and only the code gets abbreviated.
CODE_MAX = 10
_CODE_OVERRIDES = {
    "In Progress": "INPROGRES",
    "Not Executed": "NOTEXEC",
}


def code_for(name: str) -> str:
    """The <=10 character code for a reference value, keeping it readable where it fits."""
    if name in _CODE_OVERRIDES:
        return _CODE_OVERRIDES[name]
    return name if len(name) <= CODE_MAX else name.replace(" ", "")[:CODE_MAX]


# --- Item vocabulary ---------------------------------------------------------------------------
ITEM_TYPES = ["Test", "Story", "Bug", "Task"]
ITEM_STATUSES = ["Backlog", "In Progress", "In Review", "Done", "Closed"]
PRIORITIES = ["Blocker", "Critical", "High", "Medium", "Low"]
RCA_VALUES = [
    "Code Defect", "Test Data", "Environment", "Requirement Gap",
    "Third Party", "Flaky Test", "Configuration",
]
FUNCTIONAL_AREAS = ["Checkout", "Payments", "Search", "Account", "Reporting", "Notifications"]

# --- Refresh ------------------------------------------------------------------------------------
#
# Every mv_* is a snapshot. V13__analytics_refresh_log.sql defines the function that refreshes
# them in dependency order and records the outcome. Loading without calling it leaves every panel
# empty, which reads as a broken dashboard rather than as stale data.
REFRESH_SQL = "SELECT * FROM athena.refresh_analytics_views()"
