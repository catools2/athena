from test_data.factories.spec_factory import ApiSpecFactory


def build_spec() -> dict:
    return ApiSpecFactory.to_dict()

