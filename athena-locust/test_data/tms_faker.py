from test_data.factories.tms_factory import (
    ItemTypeFactory,
    StatusFactory,
    PriorityFactory,
    TestCycleFactory,
    ItemFactory,
    ItemMetadataFactory,
    StatusTransitionFactory,
    TestExecutionFactory,
)


def build_item_type(id: int | None = None) -> dict:
    result = ItemTypeFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_item_type_code() -> str:
    return ItemTypeFactory().code


def build_item_type_name() -> str:
    return ItemTypeFactory().name


def build_status(id: int | None = None) -> dict:
    result = StatusFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_status_code() -> str:
    return StatusFactory().code


def build_status_name() -> str:
    return StatusFactory().name


def build_priority(id: int | None = None) -> dict:
    result = PriorityFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_priority_code() -> str:
    return PriorityFactory().code


def build_priority_name() -> str:
    return PriorityFactory().name


def build_test_cycle(id: int | None = None) -> dict:
    result = TestCycleFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_test_cycle_name() -> str:
    return TestCycleFactory().name


def build_test_cycle_code() -> str:
    return TestCycleFactory().code


def build_item(id: int | None = None) -> dict:
    item_dict = ItemFactory.to_dict()
    if id is not None:
        item_dict["id"] = id
    return item_dict


def build_item_code() -> str:
    return ItemFactory().code


def build_item_name() -> str:
    return ItemFactory().name


def build_item_metadata(key: str | None = None, value: str | None = None) -> dict:
    md_dict = ItemMetadataFactory.to_dict()
    if key is not None:
        md_dict["name"] = key
    if value is not None:
        md_dict["value"] = value
    return md_dict


def build_item_metadata_key() -> str:
    # Backwards-compatible alias; underlying field is 'name'
    return ItemMetadataFactory().name


def build_item_metadata_name() -> str:
    return ItemMetadataFactory().name


def build_item_metadata_value() -> str:
    return ItemMetadataFactory().value


def build_status_transition(id: int | None = None) -> dict:
    st_dict = StatusTransitionFactory.to_dict()
    if id is not None:
        st_dict["id"] = id

    return st_dict


def build_test_execution(id: int | None = None) -> dict:
    exec_dict = TestExecutionFactory.to_dict()
    if id is not None:
        exec_dict["id"] = id
    return exec_dict
