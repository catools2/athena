package org.catools.athena.tms.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "itemFeignClient")
public interface ItemFeignClient {

  @RequestLine("GET /tms/item?keyword={keyword}")
  TypedResponse<ItemDto> search(@Param String keyword);

  @RequestLine("GET /tms/item/{id}")
  TypedResponse<ItemDto> getById(@Param Long id);

  @RequestLine("POST /tms/item")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(ItemDto item);

}
