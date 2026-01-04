from flask.cli import get_version

from tasks.athena_task_set import AthenaTaskSet
from test_data.spec_faker import build_spec


class SpecTaskSet(AthenaTaskSet):
    abstract = True

    specs = []

    def add_spec(self):
        if not self.get_version():
            self.add_version()

        spec = build_spec()
        with self.client.post("/spec/spec", name="AddSpec", json=spec) as response:
            if response.status_code in (200, 201, 208):
                spec["id"] = int(response.headers.get("entity_id"))
                SpecTaskSet.specs.append(spec)

    @staticmethod
    def get_spec():
        if not SpecTaskSet.specs:
            return None
        return SpecTaskSet.specs[0]

