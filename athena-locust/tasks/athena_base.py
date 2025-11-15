from locust import events, FastHttpUser

from plugins.locust_exporter import LocustExporter
from utils.logger_utils import get_logger

logger = get_logger(__name__)
TRUNC = 1000  # chars to keep from bodies


@events.request.add_listener
def log_request(request_type, name, response_time, response_length, response=None, context=None, exception=None,
                **kwargs):
    ctx = context or {}
    resp_request = response.request

    req_method = resp_request.method if resp_request else ctx.get("req_method", request_type)
    req_url = resp_request.url if resp_request else ctx.get("req_url", name)
    req_headers = resp_request.headers if resp_request else ctx.get("req_headers") or {}
    req_params = resp_request.params if resp_request and 'params' in vars(resp_request) else ctx.get("req_params")
    req_body = resp_request.payload if resp_request and 'payload' in vars(resp_request) else ctx.get("req_payload")

    if req_body is None:
        req_body = resp_request.payload if resp_request and 'json' in vars(resp_request) else ctx.get("req_json")

    if req_body is None:
        req_body = ctx.get("req_data", None)

    status = getattr(response, "status_code", "N/A")
    res_headers = getattr(response, "headers", {})

    if res_headers is None:
        res_headers = {}

    try:
        res_text = (response.text or "")[:TRUNC]
    except Exception:
        res_text = str(getattr(response, "content", b""))[:TRUNC]

    req_log = "\n--- REQUEST ---\n" \
              f"Method : {req_method}\n" \
              f"Name   : {name}\n" \
              f"URL    : {req_url}\n" \
              f"Params : {req_params}\n" \
              f"Headers: {req_headers}\n" \
              f"Body   :\n{str(req_body)[:TRUNC]}\n" \
              "--- RESPONSE ---\n" \
              f"Status : {status}\n" \
              f"Time   : {response_time:.1f} ms\n" \
              f"Size   : {response_length} bytes\n" \
              f"Headers: {dict(res_headers)}\n" \
              f"Body   :\n{res_text}\n" \
              "-------------------------"

    if exception:
        logger.warning(f"***************************")
        logger.warning(f"ctx= {ctx}")
        logger.warning(f"kwargs= {kwargs}")
        logger.warning(f"response= {vars(response)}")
        logger.warning(f"response.request= {vars(resp_request)}")
        logger.warning(f"***************************")

        logger.warning(
            req_log +
            "\n--- REQUEST FAILED ---\n"
            f"Error  : {exception}\n"
            f"Time   : {response_time:.1f} ms\n"
            "-----------------------"
        )
        return

    logger.debug(req_log)


class AthenaBase(FastHttpUser, LocustExporter):
    connection_timeout = 120.0
    network_timeout = 120.0
    abstract = True

    def __init__(self, environment, **kwargs):
        super().__init__(environment)
        self.logger = logger
