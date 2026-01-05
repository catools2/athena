package org.catools.athena.core.feign;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Complete page response wrapper for Feign clients.
 * Handles both flat and nested Page JSON structures from Spring Data.
 *
 * Spring Boot may return pagination metadata in two formats:
 * 1. Flat: {"content": [...], "size": 10, "number": 0, ...}
 * 2. Nested: {"content": [...], "page": {"size": 10, "number": 0, ...}}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {

  // Content from Slice
  @JsonProperty("content")
  private List<T> content;

  // Pagination info - can be at top level or nested in "page" object
  @JsonProperty("number")
  @JsonAlias({"pageNumber"})
  private int number;

  @JsonProperty("size")
  private int size;

  @JsonProperty("numberOfElements")
  private int numberOfElements;

  // Page-specific properties
  @JsonProperty("totalElements")
  private long totalElements;

  @JsonProperty("totalPages")
  private int totalPages;

  // Navigation flags from Slice
  @JsonProperty("first")
  private boolean first;

  @JsonProperty("last")
  private boolean last;

  @JsonProperty("empty")
  private boolean empty;

  // Pageable and Sort information (commonly included in JSON)
  @JsonProperty("pageable")
  private PageableInfo pageable;

  @JsonProperty("sort")
  private SortInfo sort;

  /**
   * Handle nested "page" object that Spring Boot sometimes uses
   * Example JSON: {"content": [...], "page": {"size": 10, "number": 0, ...}}
   */
  @JsonAnySetter
  public void handleNested(String key, Object value) {
    if ("page".equals(key) && value instanceof Map) {
      Map<?, ?> pageMap = (Map<?, ?>) value;
      if (pageMap.containsKey("size")) this.size = ((Number) pageMap.get("size")).intValue();
      if (pageMap.containsKey("number")) this.number = ((Number) pageMap.get("number")).intValue();
      if (pageMap.containsKey("totalElements")) this.totalElements = ((Number) pageMap.get("totalElements")).longValue();
      if (pageMap.containsKey("totalPages")) this.totalPages = ((Number) pageMap.get("totalPages")).intValue();
      if (pageMap.containsKey("numberOfElements")) this.numberOfElements = ((Number) pageMap.get("numberOfElements")).intValue();
      if (pageMap.containsKey("first")) this.first = (Boolean) pageMap.get("first");
      if (pageMap.containsKey("last")) this.last = (Boolean) pageMap.get("last");
      if (pageMap.containsKey("empty")) this.empty = (Boolean) pageMap.get("empty");
    }
  }

  /**
   * Convenience method - Spring Data Page uses "number" but many callers expect "pageNumber"
   * Lombok's @Data will generate getNumber/setNumber for the field, this provides compatibility alias
   */
  public int getPageNumber() {
    return number;
  }

  /**
   * Pageable information nested object
   */

  /**
   * Pageable information nested object
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PageableInfo {
    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("offset")
    private long offset;

    @JsonProperty("paged")
    private boolean paged;

    @JsonProperty("unpaged")
    private boolean unpaged;

    @JsonProperty("sort")
    private SortInfo sort;
  }

  /**
   * Sort information nested object
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SortInfo {
    @JsonProperty("sorted")
    private boolean sorted;

    @JsonProperty("unsorted")
    private boolean unsorted;

    @JsonProperty("empty")
    private boolean empty;
  }
}
