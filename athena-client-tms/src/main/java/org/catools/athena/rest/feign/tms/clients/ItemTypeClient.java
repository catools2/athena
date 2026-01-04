package org.catools.athena.rest.feign.tms.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.catools.athena.model.tms.ItemTypeDto;

interface ItemTypeClient {

  @RequestLine("GET /tms/itemType?keyword={keyword}")
  ItemTypeDto search(
      @Param("keyword")
      String keyword);

  @RequestLine("POST /tms/itemType")
  @Headers("Content-Type: application/json")
  void saveOrUpdate(ItemTypeDto itemType);

}
