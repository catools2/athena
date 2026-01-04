"""
Locust user class for the Athena Pipeline API.

Simulates pipeline operations: save/update, get by id, get last pipeline,
update end date, and pipeline execution operations.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.pipeline.pipelines import (
    AddOrUpdatePipeline,
    GetPipelineById,
    GetLastPipeline,
    UpdatePipelineEndDate,
    AddExecution,
    GetExecutionById,
    AddExecutionStatus,
    GetExecutionStatusById,
    AddScenarioExecution,
    GetScenarioExecutionById
)

class AthenaPipeline(AthenaBase):
    wait_time = between(1, 2)

    tasks = [
        (AddOrUpdatePipeline, 11),
        (GetPipelineById, 15),
        (GetLastPipeline, 15),
        (UpdatePipelineEndDate, 5),
        (AddExecution, 11),
        (GetExecutionById, 7),
        (AddExecutionStatus, 10),
        (GetExecutionStatusById, 8),
        (AddScenarioExecution, 10),
        (GetScenarioExecutionById, 8),
    ]
