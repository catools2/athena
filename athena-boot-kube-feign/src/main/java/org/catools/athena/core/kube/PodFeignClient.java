package org.catools.athena.core.kube;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.common.configs.OpenFeignConfiguration;
import org.catools.athena.model.kube.PodDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Set;

@FeignClient(value = "podFeignClient", configuration = OpenFeignConfiguration.class)
public interface PodFeignClient {

  @RequestLine("GET /pods?project={project}&namespace={namespace}")
  TypedResponse<Set<PodDto>> getAll(@Param String project, @Param String namespace);

  @RequestLine("GET /pod?name={name}&namespace={namespace}")
  TypedResponse<PodDto> getByNameAndNamespace(@Param String name, @Param String namespace);

  @RequestLine("GET /pod/{id}")
  TypedResponse<PodDto> getById(@Param Long id);

  @RequestLine("POST /pod")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(PodDto pod);

}
