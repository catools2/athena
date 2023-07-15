package org.catools.ws.enums;

import org.catools.common.collections.CList;

public enum CHttpStatusCode {
  OK_200(200),
  CREATED_201(201),
  ACCEPTED_202(202),
  NOT_AUTHORITATIVE_203(203),
  NO_CONTENT_204(204),
  RESET_205(205),
  PARTIAL_206(206),
  MULT_CHOICE_300(300),
  MOVED_PERM_301(301),
  MOVED_TEMP_302(302),
  SEE_OTHER_303(303),
  NOT_MODIFIED_304(304),
  USE_PROXY_305(305),
  MFA_WINDOW_TRIGGER_333(333),
  BAD_REQUEST_400(400),
  UNAUTHORIZED_401(401),
  PAYMENT_REQUIRED_402(402),
  FORBIDDEN_403(403),
  NOT_FOUND_404(404),
  BAD_METHOD_405(405),
  NOT_ACCEPTABLE_406(406),
  PROXY_AUTH_407(407),
  CLIENT_TIMEOUT_408(408),
  CONFLICT_409(409),
  GONE_410(410),
  LENGTH_REQUIRED_411(411),
  PRECON_FAILED_412(412),
  ENTITY_TOO_LARGE_413(413),
  REQ_TOO_LONG_414(414),
  UNSUPPORTED_TYPE_415(415),
  INTERNAL_ERROR_500(500),
  NOT_IMPLEMENTED_501(501),
  BAD_GATEWAY_502(502),
  UNAVAILABLE_503(503),
  GATEWAY_TIMEOUT_504(504),
  VERSION_505(505);

  private int code;

  CHttpStatusCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static CHttpStatusCode getByCode(int code) {
    return new CList<>(values()).getFirstOrNull(v -> v.getCode() == code);
  }
}
