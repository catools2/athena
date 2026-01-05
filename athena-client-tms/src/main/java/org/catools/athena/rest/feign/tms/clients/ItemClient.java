package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.tms.ItemDto;

interface ItemClient {

  @RequestLine("GET /tms/item?keyword={keyword}")
  ItemDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /tms/item")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(ItemDto item);

}
