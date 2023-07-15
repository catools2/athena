package org.catools.ws.utils;

import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.NotImplementedException;
import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpRequest;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.CFilterListener;

import javax.annotation.Nullable;

@UtilityClass
public class CRestAssuredUtil {

  public static CHttpResponse send(CHttpRequest request, @Nullable CFilterListener listener) {
    return send(RestAssuredConfig.newConfig(), request, listener);
  }

  public static CHttpResponse send(
      RestAssuredConfig config, CHttpRequest request, @Nullable CFilterListener listener) {
    RequestSpecification requestSpecification = request.toRequestSpecification(config);
    if (listener != null) {
      requestSpecification.filter(
          (requestSpec, responseSpec, ctx) -> {
            request
                .getFilterListener()
                .values()
                .forEach(l -> l.invoke(requestSpec, responseSpec, ctx));
            listener.invoke(requestSpec, responseSpec, ctx);
            return ctx.next(requestSpec, responseSpec);
          });
    }

    for (MultiPartSpecification multiPart : request.getMultiParts()) {
      requestSpecification.multiPart(multiPart);
    }

    if (CHttpRequestType.GET.equals(request.getRequestType())) {
      return new CHttpResponse(request, requestSpecification.get());
    }

    if (CHttpRequestType.POST.equals(request.getRequestType())) {
      return new CHttpResponse(request, requestSpecification.post());
    }

    if (CHttpRequestType.PUT.equals(request.getRequestType())) {
      return new CHttpResponse(request, requestSpecification.put());
    }

    if (CHttpRequestType.DELETE.equals(request.getRequestType())) {
      return new CHttpResponse(request, requestSpecification.delete());
    }

    if (CHttpRequestType.PATCH.equals(request.getRequestType())) {
      return new CHttpResponse(request, requestSpecification.patch());
    }

    throw new NotImplementedException("There is implementation for request " + request);
  }
}
