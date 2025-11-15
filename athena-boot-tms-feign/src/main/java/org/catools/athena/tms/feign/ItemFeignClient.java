package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "itemFeignClient")
public interface ItemFeignClient {

  @RequestLine("GET /item?keyword={keyword}")
  TypedResponse<ItemDto> search(@Param String keyword);

  @RequestLine("GET /item/{id}")
  TypedResponse<ItemDto> getById(@Param Long id);

  @RequestLine("POST /item")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ItemDto item);

}
