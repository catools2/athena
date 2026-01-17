from locust import events, FastHttpUser

from plugins.locust_exporter import LocustExporter
from utils.logger_utils import get_logger

logger = get_logger(__name__)
TRUNC = 1000  # chars to keep from bodies


@events.request.add_listener
def log_request(request_type, name, response_time, response_length, response=None, context=None, exception=None,
                **kwargs):
    ctx = context or {}
    # Be defensive: response or response.request can be None when failures happen early
    resp_request = getattr(response, "request", None) if response is not None else None

    req_method = (resp_request.method if resp_request is not None else ctx.get("req_method", request_type))
    req_url = (getattr(resp_request, "url", None) if resp_request is not None else None) or ctx.get("req_url", name)
    req_headers = (getattr(resp_request, "headers", None) if resp_request is not None else None) or ctx.get(
        "req_headers") or {}

    # Params/payload keys may vary based on how the request was made; try several common attributes/ctx keys
    req_params = None
    if resp_request is not None and hasattr(resp_request, "params"):
        req_params = resp_request.params
    else:
        req_params = ctx.get("req_params")

    req_body = None
    if resp_request is not None and hasattr(resp_request, "payload"):
        req_body = resp_request.payload
    else:
        req_body = ctx.get("req_payload")

    if req_body is None:
        if resp_request is not None and hasattr(resp_request, "json"):
            req_body = getattr(resp_request, "payload", None)
        else:
            req_body = ctx.get("req_json")

    if req_body is None:
        req_body = ctx.get("req_data")

    status = getattr(response, "status_code", "N/A")
    res_headers = getattr(response, "headers", {}) if response is not None else {}
    if res_headers is None:
        res_headers = {}

    try:
        res_text = ((response.text or "")[:TRUNC]) if response is not None else ""
    except Exception:
        res_text = str(getattr(response, "content", b""))[:TRUNC]

    req_log = "\n--- REQUEST ---\n" \
              f"Method : {req_method}\n" \
              f"Name   : {name}\n" \
              f"URL    : {req_url}\n" \
              f"Params : {req_params}\n" \
              f"Headers: {dict(req_headers) if isinstance(req_headers, dict) else req_headers}\n" \
              f"Body   :\n{str(req_body)[:TRUNC]}\n" \
              "--- RESPONSE ---\n" \
              f"Status : {status}\n" \
              f"Time   : {response_time:.1f} ms\n" \
              f"Size   : {response_length} bytes\n" \
              f"Headers: {dict(res_headers) if isinstance(res_headers, dict) else dict(res_headers)}\n" \
              f"Body   :\n{res_text}\n" \
              "-------------------------"

    if exception:
        # Avoid accessing vars() on None objects
        logger.warning("***************************")
        logger.warning(f"ctx= {ctx}")
        logger.warning(f"kwargs= {kwargs}")
        if response is not None:
            try:
                logger.warning(f"response= {vars(response)}")
            except Exception:
                logger.warning("response= <uninspectable>")
            if resp_request is not None:
                try:
                    logger.warning(f"response.request= {vars(resp_request)}")
                except Exception:
                    logger.warning("response.request= <uninspectable>")
        logger.warning("***************************")

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
    connection_timeout = 60.0
    network_timeout = 600.0
    abstract = True

    def __init__(self, environment, **kwargs):
        super().__init__(environment)
        self.logger = logger
