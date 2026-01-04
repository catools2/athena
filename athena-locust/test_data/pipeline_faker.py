from test_data.factories.pipeline_factory import PipelineFactory, PipelineExecutionFactory, PipelineExecutionStatusFactory, PipelineScenarioExecutionFactory


def build_pipeline() -> dict:
    return PipelineFactory.to_dict()


def build_pipeline_execution(pipeline_id: int | None = None) -> dict:
    return PipelineExecutionFactory.to_dict(pipeline_id=pipeline_id)


def build_pipeline_execution_status(id: int | None = None) -> dict:
    return PipelineExecutionStatusFactory.to_dict(id=id)


def build_pipeline_scenario_execution(pipeline_id: int, id: int | None = None) -> dict:
    return PipelineScenarioExecutionFactory.to_dict(pipeline_id=pipeline_id, id=id)
