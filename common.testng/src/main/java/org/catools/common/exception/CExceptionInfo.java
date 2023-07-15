package org.catools.common.exception;

import org.catools.common.collections.CSet;
import org.catools.common.utils.CStringUtil;

import java.util.Objects;

public class CExceptionInfo {
  private String type = CStringUtil.EMPTY;
  private String message = CStringUtil.EMPTY;
  private String stackTrace = CStringUtil.EMPTY;

  public CExceptionInfo() {
  }

  public CExceptionInfo(Throwable t) {
    if (t != null) {
      type = t.getClass().getName();
      message = t.getLocalizedMessage();
      stackTrace = CExceptionUtil.getStackTrace(t);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CExceptionInfo that = (CExceptionInfo) o;
    return Objects.equals(type, that.type)
        && Objects.equals(message, that.message)
        && Objects.equals(stackTrace, that.stackTrace);
  }

  public String getAllInfo() {
    return new CSet<>(type, message, stackTrace).getAll(s -> CStringUtil.isNotBlank(s)).join("\n");
  }

  public String getMessage() {
    return message;
  }

  public CExceptionInfo setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public CExceptionInfo setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
    return this;
  }

  public String getType() {
    return type;
  }

  public CExceptionInfo setType(String type) {
    this.type = type;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, message, stackTrace);
  }

  @Override
  public String toString() {
    return "CExceptionInfo{"
        + "type='"
        + type
        + '\''
        + ", message='"
        + message
        + '\''
        + ", stackTrace='"
        + stackTrace
        + '\''
        + '}';
  }
}
