package org.catools.athena.rest.feign.git.client;

import feign.Headers;
import feign.RequestLine;
import feign.Response;
import org.catools.athena.model.git.CommitDto;


public interface CommitClient {
  @RequestLine("POST /git/commit")
  @Headers("Content-Type: application/json")
  Response save(CommitDto commit);
}
