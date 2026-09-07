package org.catools.athena.core.kube;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {

  @JsonProperty("content")
  private List<T> content;

  @JsonProperty("number")
  @JsonAlias({"pageNumber"})
  private int number;

  @JsonProperty("size")
  private int size;

  @JsonProperty("numberOfElements")
  private int numberOfElements;

  @JsonProperty("totalElements")
  private long totalElements;

  @JsonProperty("totalPages")
  private int totalPages;

  @JsonProperty("first")
  private boolean first;

  @JsonProperty("last")
  private boolean last;

  @JsonProperty("empty")
  private boolean empty;

  @JsonAnySetter
  public void handleNested(String key, Object value) {
    if ("page".equals(key) && value instanceof Map<?, ?> pageMap) {
      if (pageMap.containsKey("size")) this.size = ((Number) pageMap.get("size")).intValue();
      if (pageMap.containsKey("number")) this.number = ((Number) pageMap.get("number")).intValue();
      if (pageMap.containsKey("totalElements"))
        this.totalElements = ((Number) pageMap.get("totalElements")).longValue();
      if (pageMap.containsKey("totalPages"))
        this.totalPages = ((Number) pageMap.get("totalPages")).intValue();
      if (pageMap.containsKey("numberOfElements"))
        this.numberOfElements = ((Number) pageMap.get("numberOfElements")).intValue();
      if (pageMap.containsKey("first")) this.first = (Boolean) pageMap.get("first");
      if (pageMap.containsKey("last")) this.last = (Boolean) pageMap.get("last");
      if (pageMap.containsKey("empty")) this.empty = (Boolean) pageMap.get("empty");
    }
  }

  public int getPageNumber() {
    return number;
  }
}