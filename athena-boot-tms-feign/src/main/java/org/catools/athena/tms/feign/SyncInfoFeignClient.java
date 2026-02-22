package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.tms.SyncInfoDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "syncInfoFeignClient")
public interface SyncInfoFeignClient {

  @RequestLine("GET /syncInfo?action={action}&component={component}&project={project}")
  TypedResponse<SyncInfoDto> search(
      @Param("action") String action,
      @Param("component") String component,
      @Param("project") String project);

  @RequestLine("GET /syncInfo/{id}")
  TypedResponse<SyncInfoDto> getById(@Param("id") Long id);

  @RequestLine("POST /syncInfo")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(SyncInfoDto syncInfo);
}
