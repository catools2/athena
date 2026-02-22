package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.tms.ItemTypeDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "itemTypeFeignClient")
public interface ItemTypeFeignClient {

  @RequestLine("GET /itemType?keyword={keyword}")
  TypedResponse<ItemTypeDto> search(@Param("keyword") String keyword);

  @RequestLine("GET /itemType/{id}")
  TypedResponse<ItemTypeDto> getById(@Param("id") Long id);

  @RequestLine("POST /itemType")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ItemTypeDto itemType);
}
