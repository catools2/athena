from test_data.factories.tms_factory import (
    ItemTypeFactory,
    StatusFactory,
    PriorityFactory,
    SyncInfoFactory,
    TestCycleFactory,
    ItemFactory,
    ItemMetadataFactory,
    StatusTransitionFactory,
    TestExecutionFactory,
)


def get_item_type(id: int | None = None) -> dict:
    result = ItemTypeFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def get_item_type_code() -> str:
    return ItemTypeFactory().code


def get_item_type_name() -> str:
    return ItemTypeFactory().name


def get_status(id: int | None = None) -> dict:
    result = StatusFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def get_status_code() -> str:
    return StatusFactory().code


def get_status_name() -> str:
    return StatusFactory().name


def get_priority(id: int | None = None) -> dict:
    result = PriorityFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def get_priority_code() -> str:
    return PriorityFactory().code


def get_priority_name() -> str:
    return PriorityFactory().name


def get_sync_info(project_id: int | None = None) -> dict:
    result = SyncInfoFactory.to_dict()
    if project_id is not None:
        result["projectId"] = project_id
    return result


def get_sync_info_action() -> str:
    return SyncInfoFactory().action


def get_test_cycle(id: int | None = None, version_id: int | None = None) -> dict:
    result = TestCycleFactory.to_dict()
    if id is not None:
        result["id"] = id
    if version_id is not None:
        result["versionId"] = version_id
    return result


def get_test_cycle_name() -> str:
    return TestCycleFactory().name


def get_item(id: int | None = None, project_id: int | None = None) -> dict:
    item_dict = ItemFactory.to_dict()
    if id is not None:
        item_dict["id"] = id
    if project_id is not None:
        item_dict["projectId"] = project_id
    return item_dict


def get_item_code() -> str:
    return ItemFactory().code


def get_item_name() -> str:
    return ItemFactory().name


def get_item_metadata(key: str | None = None, value: str | None = None) -> dict:
    md_dict = ItemMetadataFactory.to_dict()
    if key is not None:
        md_dict["key"] = key
    if value is not None:
        md_dict["value"] = value
    return md_dict


def get_item_metadata_key() -> str:
    return ItemMetadataFactory().key


def get_item_metadata_value() -> str:
    return ItemMetadataFactory().value


def get_status_transition(
    id: int | None = None,
    from_status_code: str | None = None,
    to_status_code: str | None = None,
    item_code: str | None = None,
    author_id: int | None = None,
) -> dict:
    st_dict = StatusTransitionFactory.to_dict()
    if id is not None:
        st_dict["id"] = id
    if from_status_code is not None:
        st_dict["fromStatusCode"] = from_status_code
    if to_status_code is not None:
        st_dict["toStatusCode"] = to_status_code
    if item_code is not None:
        st_dict["itemCode"] = item_code
    if author_id is not None:
        st_dict["authorId"] = author_id
    return st_dict


def get_test_execution(
    id: int | None = None,
    cycle_code: str | None = None,
    item_code: str | None = None,
    status_code: str | None = None,
    executor_id: int | None = None,
) -> dict:
    exec_dict = TestExecutionFactory.to_dict()
    if id is not None:
        exec_dict["id"] = id
    if cycle_code is not None:
        exec_dict["cycleCode"] = cycle_code
    if item_code is not None:
        exec_dict["itemCode"] = item_code
    if status_code is not None:
        exec_dict["statusCode"] = status_code
    if executor_id is not None:
        exec_dict["executorId"] = executor_id
    return exec_dict