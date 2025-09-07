# TMS task modules

"""
This package contains Locust task definitions for the Athena TMS (Test Management System)
endpoints.  Each module defines a small set of tasks that exercise a specific
resource type.  The task classes inherit from :class:`AthenaTmsTaskSet`, which
provides common helper methods for creating and retrieving entities on the
server.  The helpers generate minimal but valid payloads for the TMS
REST API, parse the response to extract an identifier, and store it for
subsequent retrieval operations.

To register these tasks with a Locust user class, import the classes you need
from this package and assign them to the `tasks` attribute of your user class
(see ``athena_tms.py`` for an example).
"""
