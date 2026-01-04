import json
import random

from tasks.athena_task_set import AthenaTaskSet
from test_data.pipeline_faker import build_pipeline, build_pipeline_execution, build_pipeline_execution_status, \
    build_pipeline_scenario_execution


class PipelineTaskSet(AthenaTaskSet):
    abstract = True

    pipelines = []
    executions = []
    statuses = []
    scenario_executions = []

    def add_or_update_pipeline(self):
        for i in range(10):
            project = AthenaTaskSet.get_project()
            if not project:
                self.add_project()

            project = AthenaTaskSet.get_project_with_environments()
            if not project:
                self.add_environment()

        if len(PipelineTaskSet.statuses) < 1:
            self.add_execution_status()

        pipeline = build_pipeline()
        with self.client.post("/pipeline/pipeline", name="AddOrUpdatePipeline", json=pipeline) as response:
            if response.status_code in (200, 201, 208):
                pipeline["id"] = int(response.headers.get("entity_id"))
                PipelineTaskSet.pipelines.append(pipeline)

    def add_execution(self):
        pipeline = PipelineTaskSet.get_pipeline()

        if not pipeline:
            self.add_or_update_pipeline()
            pipeline = PipelineTaskSet.get_pipeline()

        if not self.get_user():
            self.add_user()

        execution = build_pipeline_execution(pipeline_id=pipeline["id"])
        with self.client.post("/pipeline/execution", name="AddExecution", json=execution) as response:
            if response.status_code in (200, 201, 208):
                execution["id"] = int(response.headers.get("entity_id"))
                PipelineTaskSet.executions.append(execution)

    def add_execution_status(self):
        status = build_pipeline_execution_status()
        with self.client.post("/pipeline/execution_status", name="AddExecutionStatus", json=status) as response:
            if response.status_code in (200, 201, 208):
                status["id"] = int(response.headers.get("entity_id"))
                PipelineTaskSet.statuses.append(status)

    def add_scenario_execution(self):
        pipeline = PipelineTaskSet.get_pipeline()

        if not pipeline:
            self.add_or_update_pipeline()
            pipeline = PipelineTaskSet.get_pipeline()

        if not self.get_user():
            self.add_user()

        scenario = build_pipeline_scenario_execution(pipeline_id=pipeline.get("id"))
        with self.client.post("/pipeline/scenario", name="AddScenarioExecution", json=scenario) as response:
            if response.status_code in (200, 201):
                scenario["id"] = int(response.headers.get("entity_id", "0") or 0)
                PipelineTaskSet.scenario_executions.append(scenario)

    @staticmethod
    def get_pipeline():
        if len(PipelineTaskSet.pipelines) == 0:
            return None
        return random.choice(PipelineTaskSet.pipelines)

    @staticmethod
    def get_execution():
        if len(PipelineTaskSet.executions) == 0:
            return None
        return random.choice(PipelineTaskSet.executions)

    @staticmethod
    def get_status():
        if len(PipelineTaskSet.statuses) == 0:
            return None
        return random.choice(PipelineTaskSet.statuses)

    @staticmethod
    def get_scenario_execution():
        if len(PipelineTaskSet.scenario_executions) == 0:
            return None
        return PipelineTaskSet.scenario_executions[0]
