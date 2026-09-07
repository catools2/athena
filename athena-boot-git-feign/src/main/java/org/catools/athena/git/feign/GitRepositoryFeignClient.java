package org.catools.athena.git.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.TypedResponse;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.GitRepositoryInventoryDto;
import org.catools.athena.model.git.GitRepositorySummaryDto;
import org.catools.athena.model.git.GitRepositoryTrendPointDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value = "gitRepositoryFeignClient")
public interface GitRepositoryFeignClient {

  @RequestLine("GET /summary?keyword={keyword}&host={host}&freshness={freshness}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<GitRepositorySummaryDto> getSummary(
    @Param("keyword") String keyword,
    @Param("host") String host,
    @Param("freshness") String freshness,
    @Param("windowDays") Integer windowDays);

  @RequestLine("GET /trend?keyword={keyword}&host={host}&freshness={freshness}&windowDays={windowDays}")
  @Headers("Accept: application/json")
  TypedResponse<List<GitRepositoryTrendPointDto>> getTrend(
    @Param("keyword") String keyword,
    @Param("host") String host,
    @Param("freshness") String freshness,
    @Param("windowDays") Integer windowDays);

  @RequestLine("GET /all?page={page}&size={size}&sort={sort}&direction={direction}&keyword={keyword}&host={host}&freshness={freshness}")
  @Headers("Accept: application/json")
  TypedResponse<PageResponse<GitRepositoryInventoryDto>> getAll(
    @Param("page") int page,
    @Param("size") int size,
    @Param("sort") String sort,
    @Param("direction") String direction,
    @Param("keyword") String keyword,
    @Param("host") String host,
    @Param("freshness") String freshness);

  @RequestLine("GET /repo?keyword={keyword}")
  TypedResponse<GitRepositoryDto> search(@Param("keyword") String keyword);

  @RequestLine("GET /repo/{id}")
  TypedResponse<GitRepositoryDto> getById(@Param("id") Long id);

  @RequestLine("POST /repo")
  @Headers("Content-Type: application/json")
  TypedResponse<Void> saveOrUpdate(GitRepositoryDto repository);
}
