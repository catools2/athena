from locust import task

from tasks.pipeline.pipeline_task_set import PipelineTaskSet


class AddOrUpdatePipeline(PipelineTaskSet):

    @task
    def add_or_update_pipeline_task(self):
        self.add_or_update_pipeline()


class AddExecution(PipelineTaskSet):

    @task
    def add_execution_task(self):
        self.add_execution()


class GetPipelineById(PipelineTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(PipelineTaskSet.pipelines) < 1:
            self.add_or_update_pipeline()

    @task
    def get_pipeline_by_id_task(self):
        pipeline = PipelineTaskSet.get_pipeline()
        self.client.get(f"/pipeline/pipeline/{pipeline['id']}", name="GetPipelineById")


class GetExecutionById(PipelineTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(PipelineTaskSet.executions) < 1:
            self.add_execution()

    @task
    def get_execution_by_id_task(self):
        execution = PipelineTaskSet.get_execution()
        self.client.get(f"/pipeline/execution/{execution['id']}", name="GetExecutionById")


class GetLastPipeline(PipelineTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(PipelineTaskSet.pipelines) < 1:
            self.add_or_update_pipeline()

    @task
    def get_last_pipeline_task(self):
        pipeline = PipelineTaskSet.get_pipeline()
        if not pipeline:
            return
        self.client.get(
            f"/pipeline/pipeline?name={pipeline['name']}&number={pipeline['number']}&project={pipeline['project']}&version={pipeline['version']}&environment={pipeline['environment']}",
            name="GetLastPipeline",
        )


class UpdatePipelineEndDate(PipelineTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(PipelineTaskSet.pipelines) < 1:
            self.add_or_update_pipeline()

    @task
    def update_pipeline_end_date_task(self):
        pipeline = PipelineTaskSet.get_pipeline()
        self.client.put(f"/pipeline/pipeline?pipelineId={pipeline['id']}", name="UpdatePipelineEndDate")


class AddExecutionStatus(PipelineTaskSet):

    @task
    def add_execution_status_task(self):
        self.add_execution_status()


class GetExecutionStatusById(PipelineTaskSet):
    def on_start(self) -> None:
        super().on_start()
        if len(AddExecutionStatus.statuses) < 1:
            self.add_execution_status()

    @task
    def get_execution_status_by_id_task(self):
        status = AddExecutionStatus.statuses[0] if AddExecutionStatus.statuses else {"id": 1}
        self.client.get(f"/pipeline/execution_status/{status['id']}", name="GetExecutionStatusById")

class AddScenarioExecution(PipelineTaskSet):
    def on_start(self) -> None:
        super().on_start()
        if len(AddExecutionStatus.statuses) < 1:
            self.add_execution_status()

    @task
    def add_scenario_execution_task(self):
        self.add_scenario_execution()

class GetScenarioExecutionById(PipelineTaskSet):
    def on_start(self) -> None:
        super().on_start()
        if len(PipelineTaskSet.scenario_executions) < 1:
            self.add_scenario_execution()

    @task
    def get_scenario_execution_by_id_task(self):
        scenario = PipelineTaskSet.get_scenario_execution() or {"id": 1}
        self.client.get(f"/pipeline/scenario/{scenario['id']}", name="GetScenarioExecutionById")

