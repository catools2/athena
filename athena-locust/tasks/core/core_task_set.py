from random import Random

from tasks.athena_task_set import AthenaTaskSet

random = Random()


class CoreTaskSet(AthenaTaskSet):
    abstract = True
