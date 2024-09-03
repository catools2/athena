package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.SyncInfoDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "syncInfoFeignClient")
public interface SyncInfoFeignClient {

  @RequestLine("GET /tms/syncInfo?action={action}&component={component}&project={project}")
  TypedResponse<SyncInfoDto> search(@Param String action, @Param String component, @Param String project);

  @RequestLine("GET /tms/syncInfo/{id}")
  TypedResponse<SyncInfoDto> getById(@Param Long id);

  @RequestLine("POST /tms/syncInfo")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(SyncInfoDto syncInfo);

}
