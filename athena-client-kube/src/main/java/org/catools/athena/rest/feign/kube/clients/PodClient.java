package org.catools.athena.rest.feign.kube.clients;

import feign.Headers;
import feign.RequestLine;
import org.catools.athena.model.kube.PodDto;

interface PodClient {

  @RequestLine("POST /kube/pod")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(PodDto pod);

}
