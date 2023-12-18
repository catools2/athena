package org.catools.ws.utils;

import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.common.utils.CJsonUtil;
import org.catools.metrics.configs.CMetricsConfigs;
import org.catools.metrics.model.CMetric;
import org.catools.metrics.utils.CMetricsUtils;
import org.catools.ws.enums.CHttpRequestType;
import org.catools.ws.model.CHttpRequest;
import org.catools.ws.model.CHttpResponse;
import org.catools.ws.rest.CFilterListener;

import javax.annotation.Nullable;

@Slf4j
@UtilityClass
public class CRestAssuredUtil {

  public static CHttpResponse send(CHttpRequest request, @Nullable CFilterListener listener) {
    return send(RestAssuredConfig.newConfig(), request, listener);
  }

  public static CHttpResponse send(RestAssuredConfig config, CHttpRequest request, @Nullable CFilterListener listener) {
    RequestSpecification requestSpecification = request.toRequestSpecification(config);

    if (listener != null) {
      requestSpecification.filter((requestSpec, responseSpec, ctx) -> {
        request.getFilterListener().values().forEach(l -> l.invoke(requestSpec, responseSpec, ctx));
        listener.invoke(requestSpec, responseSpec, ctx);
        return ctx.next(requestSpec, responseSpec);
      });
    }

    CDate startTime = CDate.now();

    try {
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

      if (CHttpRequestType.HEAD.equals(request.getRequestType())) {
        return new CHttpResponse(request, requestSpecification.head());
      }

      if (CHttpRequestType.OPTIONS.equals(request.getRequestType())) {
        return new CHttpResponse(request, requestSpecification.options());
      }
    } finally {
      recordPerformanceMetrics(request, startTime);
    }

    throw new NotImplementedException("There is implementation for request " + request);
  }

  private synchronized static void recordPerformanceMetrics(CHttpRequest request, CDate startTime) {
    if (!CMetricsConfigs.isWebServiceRecorderEnabled()) return;
    try {
      CList<CMetric> metrics = CList.of(
          new CMetric("REQUEST_TYPE", request.getRequestType().name(), null),
          new CMetric("TARGET", request.getTarget(), null)
      );

      if (request.getPath() != null)
        metrics.add(new CMetric("PATH", request.getPath(), null));

      if (request.getContentType() != null)
        metrics.add(new CMetric("CONTENT_TYPE", request.getContentType().toString(), null));

      if (request.getQueryParameter() != null)
        metrics.add(new CMetric("QUERY_PARAMETERS", CJsonUtil.toString(request.getQueryParameter()), null));

      if (request.getFormParameters() != null)
        metrics.add(new CMetric("FORM_PARAMETERS", CJsonUtil.toString(request.getFormParameters()), null));

      if (request.getParameters() != null)
        metrics.add(new CMetric("PARAMETERS", CJsonUtil.toString(request.getParameters()), null));

      if (request.getParameters() != null)
        metrics.add(new CMetric("HEADERS", CJsonUtil.toString(request.getHeaders()), null));

      CMetricsUtils.addMetric(
          "ApiRequest",
          startTime,
          startTime.getDurationToNow().toNanos(),
          metrics
      );
    } catch (Exception e) {
      log.warn("Failed to record performance metric.", e);
    }
  }
}
