package org.catools.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.Getter;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.types.CStaticNumberExtension;
import org.catools.common.extensions.types.CStaticObjectExtension;
import org.catools.common.extensions.types.CStaticStringExtension;
import org.catools.common.utils.CJsonUtil;
import org.catools.ws.enums.CHttpStatusCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response with all information returns from http server after call
 */
public class CHttpResponse {
  @JsonIgnore
  private final CHttpRequest request;

  @JsonIgnore
  @Getter
  private final Response response;

  @Getter
  private final byte[] content;

  @Getter
  private final long contentLength;

  public CHttpResponse(CHttpRequest request, Response response) {
    try {
      this.request = request;
      this.response = response;

      if (response != null) {
        for (Header h : response.getHeaders()) {
          this.Headers.add(new CResponseHeader(h));
        }

        if (!response.getCookies().isEmpty()) {
          this.Cookies.putAll(response.getCookies());
        }
      }

      if (response != null && response.getBody() != null) {
        this.content = response.getBody().asByteArray();
        this.contentLength = content.length;

        try {
          ContentMap.putAll(new JSONObject(new String(content)).toMap());
        } catch (JSONException e) {
          ContentMap.put("content", new String(content));
        }
      } else {
        this.content = new byte[0];
        this.contentLength = 0;
      }
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }

  // Extensions
  @Getter
  public final CResponseHeaders Headers = new CResponseHeaders();

  @Getter
  public final CMap<String, Object> ContentMap = new CHashMap<>();

  @Getter
  public final CMap<String, String> Cookies = new CHashMap<>();

  public final CStaticObjectExtension<CHttpStatusCode> StatusCode =
      new CStaticObjectExtension<>() {
        @Override
        public String getDefaultMessage(String methodDescription) {
          return String.format("%s: Status Code %s", request.getUri(), methodDescription);
        }

        @Override
        @JsonIgnore
        public CHttpStatusCode _get() {
          return response == null ? null : CHttpStatusCode.getByCode(response.getStatusCode());
        }
      };

  public final CStaticStringExtension Content =
      new CStaticStringExtension() {
        @Override
        public String getDefaultMessage(String methodDescription) {
          return String.format("%s: Content %s", request.getUri(), methodDescription);
        }

        @Override
        @JsonIgnore
        public String _get() {
          return response == null ? null : response.prettyPrint();
        }
      };

  public final CStaticStringExtension ContentType =
      new CStaticStringExtension() {
        @Override
        public String getDefaultMessage(String methodDescription) {
          return String.format("%s: Content Type %s", request.getUri(), methodDescription);
        }

        @Override
        @JsonIgnore
        public String _get() {
          return response == null ? null : response.getContentType();
        }
      };

  public final CStaticNumberExtension<Long> Length =
      new CStaticNumberExtension<>() {
        @Override
        public String getDefaultMessage(String methodDescription) {
          return String.format("%s: Length %s", request.getUri(), methodDescription);
        }

        @Override
        @JsonIgnore
        public Long _get() {
          return response == null ? null : contentLength;
        }
      };

  // Methods
  @JsonIgnore
  public JSONObject getJsonContent() {
    return new JSONObject(getContentString());
  }

  @JsonIgnore
  public JSONArray getJsonArrayContent() {
    return new JSONArray(getContentString());
  }

  @JsonIgnore
  public String getContentString() {
    return Content._get();
  }

  @JsonIgnore
  public <T> T getContent(Class<T> clazz) {
    return CJsonUtil.read(getContentString(), clazz);
  }

  @JsonIgnore
  public <T> T getContent(TypeReference<T> clazz) {
    return CJsonUtil.read(getContentString(), clazz);
  }

  @JsonIgnore
  public String getContentType() {
    return ContentType._get();
  }

  @JsonIgnore
  public CHttpStatusCode getStatusCode() {
    return StatusCode._get();
  }

  @Override
  public String toString() {
    return "CHttpResponse{"
        + '\n'
        + "StatusCode="
        + StatusCode._get()
        + ","
        + '\n'
        + "ContentType="
        + ContentType._get()
        + ","
        + '\n'
        + "Length="
        + Length._get()
        + ","
        + '\n'
        + "Headers="
        + Headers.join("\n\t\t")
        + ","
        + '\n'
        + "content="
        + getContentString()
        + ","
        + '\n'
        + '}';
  }
}
