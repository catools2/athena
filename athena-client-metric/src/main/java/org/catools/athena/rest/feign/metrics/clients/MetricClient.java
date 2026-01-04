package org.catools.athena.rest.feign.metrics.clients;

import feign.Headers;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.model.metrics.MetricDto;

public interface MetricClient {

  @RequestLine("POST /kube/metric")
  @Headers("Content-Type: application/json")
  Response saveMetric(MetricDto metric);

}
