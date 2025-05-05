from locust import HttpUser, task, between, events
from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
import time
import os

# Load Influx config from env vars or hardcode
INFLUX_URL = os.getenv("INFLUX_URL", "http://localhost:8086")
INFLUX_TOKEN = os.getenv("INFLUX_TOKEN", "_zGeqE0dYDQahcdl77il_XKbZGsloAdYreaRroNDjFgQbFjyYvOlMJLuD1ju5ZlW3GQ3GF_DKLlOfKPo1ylr0Q==")
INFLUX_ORG = os.getenv("INFLUX_ORG", "athena-org")
INFLUX_BUCKET = os.getenv("INFLUX_BUCKET", "athena-metrics")

# Initialize client once
client = InfluxDBClient(url=INFLUX_URL, token=INFLUX_TOKEN, org=INFLUX_ORG)
write_api = client.write_api(write_options=SYNCHRONOUS)

class LocustExporter:

    @events.request.add_listener
    def log_request_to_influx(request_type, name, response_time, response_length, response, context, exception, start_time, url, **kwargs):
        point = Point("locust_requests") \
            .tag("request_type", request_type) \
            .tag("name", name) \
            .field("response_time", float(response_time)) \
            .field("response_length", int(response_length)) \
            .field("success", 0 if exception else 1) \
            .time(time.time_ns())
        write_api.write(bucket=INFLUX_BUCKET, record=point)
